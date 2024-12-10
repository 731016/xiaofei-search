package com.xiaofei.site.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.model.dto.file.FileQueryRequest;
import com.xiaofei.site.search.model.vo.FileVo;
import com.xiaofei.site.search.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Service
public class FileDataSource implements SearchDataSource<FileVo>{

    @Resource
    private FileService fileService;

    @Override
    public Page<FileVo> doSearch(String searchText, int current, int pageSize) {

        FileQueryRequest fileQueryRequest = new FileQueryRequest();
        fileQueryRequest.setSearchText(searchText);
        fileQueryRequest.setCurrent(current);
        fileQueryRequest.setPageSize(pageSize);
        Page<FileVo> fileVoPage = fileService.searchFromEs(fileQueryRequest);
        return fileVoPage;
    }
}
