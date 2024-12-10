package com.xiaofei.site.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaofei.site.search.model.dto.file.FileQueryRequest;
import com.xiaofei.site.search.model.entity.FilePo;
import com.xiaofei.site.search.model.enums.FileUploadBizEnum;
import com.xiaofei.site.search.model.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
public interface FileService extends IService<FilePo> {

    Page<FileVo> listFileVoPage(FileQueryRequest fileQueryRequest);

    Page<FileVo> searchFromEs(FileQueryRequest fileQueryRequest);

    FilePo uploadFile(MultipartFile file, FileUploadBizEnum fileUploadBizEnum);

}
