package com.xiaofei.site.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/26
 */
@Service
public class ImageDataSource implements SearchDataSource<Image> {

    @Resource
    private ImageService imageService;

    @Override
    public Page<Image> doSearch(String searchText, int current, int pageSize) {
        ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
        imageQueryRequest.setSearchText(searchText);
        imageQueryRequest.setCurrent(current);
        imageQueryRequest.setPageSize(pageSize);
        Page<Image> imageByPage = imageService.getImageByPage(imageQueryRequest);
        return imageByPage;
    }
}
