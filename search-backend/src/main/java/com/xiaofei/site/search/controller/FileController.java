package com.xiaofei.site.search.controller;

import cn.hutool.core.io.FileUtil;
import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.constant.FileConstant;
import com.xiaofei.site.search.exception.BusinessException;
import com.xiaofei.site.search.manager.CosManager;
import com.xiaofei.site.search.mapper.FileMapper;
import com.xiaofei.site.search.model.dto.file.FileDownloadRequest;
import com.xiaofei.site.search.model.dto.file.UploadFileRequest;
import com.xiaofei.site.search.model.entity.FilePo;
import com.xiaofei.site.search.model.entity.User;
import com.xiaofei.site.search.model.enums.FileUploadBizEnum;
import com.xiaofei.site.search.model.vo.FileVo;
import com.xiaofei.site.search.service.FileService;
import com.xiaofei.site.search.service.UserService;
import com.xiaofei.site.search.utils.FilePoToVoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 文件接口
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private UserService userService;

    @Resource
    private CosManager cosManager;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private FileService fileService;


    /**
     * 文件上传
     *
     * @param multipartFile
     * @param uploadFileRequest
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public BaseResponse<String> uploadFileByCos(@RequestPart("file") MultipartFile multipartFile,
                                           @RequestPart UploadFileRequest uploadFileRequest, HttpServletRequest request) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        User loginUser = userService.getLoginUser(request);
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getId(), filename);
        File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            // 返回可访问地址
            return ResultUtils.success(FileConstant.COS_HOST + filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    @PostMapping("/uploadEs")
    public BaseResponse<FileVo> uploadFileByEs(@RequestPart("file") MultipartFile multipartFile,
                                             @RequestPart UploadFileRequest uploadFileRequest, HttpServletRequest request) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        FilePo filePo = fileService.uploadFile(multipartFile, fileUploadBizEnum);
        return ResultUtils.success(FilePoToVoUtils.poToVo(filePo));
    }


    // 读取文件
    @PostMapping("/download")
    public void downloadFile(@RequestBody FileDownloadRequest fileDownloadRequest, HttpServletResponse response) {
        if (fileDownloadRequest == null) {
            return;
        }
        Long id = fileDownloadRequest.getId();
        // 1. 查询文件信息
        FilePo filePo = fileMapper.selectById(id);
        if (filePo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文件不存在");
        }

        File file = new File(filePo.getFilePath());
        if (!file.exists()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文件不存在");
        }

        try {
            // 2. 设置响应头
            response.setContentType(filePo.getFileType());
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filePo.getFileName(), "UTF-8"));
            response.setHeader("Content-Length", String.valueOf(file.length()));

            // 3. 写入响应流
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 OutputStream os = response.getOutputStream()) {

                byte[] buffer = new byte[1024];
                int len;
                while ((len = bis.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }

            // 4. 更新下载次数
            filePo.setDownloadCount(filePo.getDownloadCount() + 1);
            fileMapper.updateById(filePo);

        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        }

    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 10 * 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 10M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
