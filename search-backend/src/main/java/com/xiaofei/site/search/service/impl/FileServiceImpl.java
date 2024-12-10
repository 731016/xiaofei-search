package com.xiaofei.site.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.config.FileConfig;
import com.xiaofei.site.search.constant.CommonConstant;
import com.xiaofei.site.search.exception.BusinessException;
import com.xiaofei.site.search.mapper.FileMapper;
import com.xiaofei.site.search.model.dto.file.FileEsDTO;
import com.xiaofei.site.search.model.dto.file.FileQueryRequest;
import com.xiaofei.site.search.model.entity.FilePo;
import com.xiaofei.site.search.model.enums.FileUploadBizEnum;
import com.xiaofei.site.search.model.vo.FileVo;
import com.xiaofei.site.search.service.FileService;
import com.xiaofei.site.search.utils.FilePoToVoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FilePo> implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private FileConfig fileConfig;

    @Override
    public Page<FileVo> listFileVoPage(FileQueryRequest fileQueryRequest) {
        int current = fileQueryRequest.getCurrent();
        int pageSize = fileQueryRequest.getPageSize();
        if (fileQueryRequest == null) {
            return new Page<>(current, pageSize);
        }
        String fileName = fileQueryRequest.getFileName();
        String biz = fileQueryRequest.getBiz();
        QueryWrapper<FilePo> queryWrapper = new QueryWrapper<>();


        queryWrapper.lambda()
                // 文件名模糊查询
                .like(StringUtils.isNotBlank(fileName), FilePo::getFileName, fileName)
                // 业务类型精确匹配
                .eq(StringUtils.isNotBlank(biz), FilePo::getBiz, biz)
                // 按上传时间倒序
                .orderByDesc(FilePo::getCreateTime);

        Page<FilePo> page = this.page(new Page<>(current, pageSize), queryWrapper);
        List<FilePo> filePos = page.getRecords();
        List<FileVo> fileVos = new ArrayList<>();
        if (CollUtil.isNotEmpty(filePos)) {
            for (FilePo filePo : filePos) {
                fileVos.add(FilePoToVoUtils.poToVo(filePo));
            }
        }
        Page<FileVo> fileVoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        fileVoPage.setRecords(fileVos);
        return fileVoPage;
    }


    @Override
    public Page<FileVo> searchFromEs(FileQueryRequest fileQueryRequest) {
        String searchText = fileQueryRequest.getSearchText();
        String fileName = fileQueryRequest.getFileName();
        String content = fileQueryRequest.getContent();
        // es 起始页为 0
        long current = fileQueryRequest.getCurrent() - 1;
        long pageSize = fileQueryRequest.getPageSize();
        String sortField = fileQueryRequest.getSortField();
        String sortOrder = fileQueryRequest.getSortOrder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));
        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("fileName", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("description", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("attachment.content", searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按标题检索
        if (StringUtils.isNotBlank(fileName)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("fileName", fileName));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按内容检索
        if (StringUtils.isNotBlank(content)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("attachment.content", content));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withSorts(sortBuilder).build();
        SearchHits<FileEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, FileEsDTO.class);
        Page<FileVo> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        List<FilePo> resourceList = new ArrayList<>();
        // 查出结果后，从 db 获取最新动态数据（比如点赞数）
        if (searchHits.hasSearchHits()) {
            List<SearchHit<FileEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> fileIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());
            List<FilePo> fileList = baseMapper.selectBatchIds(fileIdList);
            if (fileList != null) {
                Map<Long, List<FilePo>> idPostMap = fileList.stream().collect(Collectors.groupingBy(FilePo::getId));
                fileIdList.forEach(fileId -> {
                    if (idPostMap.containsKey(fileId)) {
                        resourceList.add(idPostMap.get(fileId).get(0));
                    } else {
                        // 从 es 清空 db 已物理删除的数据
                        String delete = elasticsearchRestTemplate.delete(String.valueOf(fileId), FileEsDTO.class);
                        log.info("delete post {}", delete);
                    }
                });
            }
        }
        List<FileVo> fileVoList = new ArrayList<>();
        if (CollUtil.isNotEmpty(resourceList)) {
            for (FilePo filePo : resourceList) {
                FileVo fileVo = FilePoToVoUtils.poToVo(filePo);
                fileVoList.add(fileVo);
            }
        }
        page.setRecords(fileVoList);
        return page;
    }

    /**
     * 生成文件名（防止重复）
     *
     * @param originalFilename
     * @return
     */
    private String generateFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        return uuid + "." + extension;
    }

    /**
     * 计算文件MD5
     *
     * @param file
     * @return
     * @throws IOException
     */
    private String calculateMD5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    @Override
    public FilePo uploadFile(MultipartFile file, FileUploadBizEnum fileUploadBizEnum) {
        try {

            //扩展名
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            // 1. 生成存储路径
            String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String fileName = generateFileName(file.getOriginalFilename());
            String relativePath = String.format("%s/%s/%s", datePath, fileUploadBizEnum.getValue(), fileName);
            String absolutePath = fileConfig.getPath() + "/" + relativePath;

            // 2. 确保目录存在
            File directory = new File(absolutePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 3. 保存文件
            File dest = new File(absolutePath);
            file.transferTo(dest);

            // 4. 计算MD5
            String md5 = calculateMD5(dest);

            // 5. 保存文件信息到数据库
            Date nowDate = new Date();

            FilePo filePo = new FilePo();
            filePo.setFileName(file.getOriginalFilename());
            filePo.setFilePath(absolutePath);
            filePo.setFileType(file.getContentType());
            filePo.setFileSize(file.getSize());
            filePo.setFileExtension(extension);
            filePo.setMd5(md5);
            filePo.setBiz(fileUploadBizEnum.getValue());
            filePo.setCreateTime(nowDate);
            filePo.setUpdateTime(nowDate);
            filePo.setIsDelete(0);


            int insert = fileMapper.insert(filePo);
            if (insert > 0) {
                //上传es
                boolean esUpload = uploadFileToEs(filePo, dest);
                // 4. 删除临时文件
                dest.delete();

                if (!esUpload) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "文件上传失败");
                }
                return filePo;
            }
            return null;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "文件上传失败");
        }
    }

    /**
     * 通过pipeline上传文件到es
     * @param filePo
     * @param file
     * @return
     */
    public boolean uploadFileToEs(FilePo filePo, File file) {
        try {
            // 1. 读取文件内容并转换为 Base64
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64Content = Base64.getEncoder().encodeToString(fileContent);

            // 2. 准备索引文档
            Map<String, Object> document = new HashMap<>();
            document.put("id", filePo.getId());
            document.put("fileName", filePo.getFileName());
            document.put("fileType", filePo.getFileType());
            document.put("content", base64Content);  // 使用 content 字段
            document.put("description", filePo.getDescription());
            document.put("userId", filePo.getUserId());
            document.put("biz", filePo.getBiz());
            document.put("createTime", filePo.getCreateTime());
            document.put("updateTime", filePo.getUpdateTime());

            // 3. 创建索引请求
            IndexRequest indexRequest = new IndexRequest("file_v3")
                    .id(filePo.getId().toString())
                    .setPipeline("attachment")
                    .source(document);

            // 4. 执行索引请求
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            return indexResponse.status() == RestStatus.CREATED
                    || indexResponse.status() == RestStatus.OK;

        } catch (Exception e) {
            log.error("上传文件到 ES 失败", e);
            return false;
        }
    }
}
