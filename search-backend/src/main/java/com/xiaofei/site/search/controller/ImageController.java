package com.xiaofei.site.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.exception.ThrowUtils;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 *
 */
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Resource
    private ImageService imageService;

    /**
     * 分页获取列表（封装类）
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Image>> listPostByPage(@RequestBody ImageQueryRequest imageQueryRequest,
                                                    HttpServletRequest request) {
        long current = imageQueryRequest.getCurrent();
        long size = imageQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Image> imagePage = imageService.getImageByPage(imageQueryRequest);
        return ResultUtils.success(imagePage);
    }

}
