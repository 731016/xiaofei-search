package com.xiaofei.site.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;

/**
 * 图片服务
 *
 */
public interface ImageService {


    /**
     * 分页获取图片
     *
     * @return
     */
    Page<Image> getImageByPage(ImageQueryRequest imageQueryRequest);
}
