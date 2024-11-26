package com.xiaofei.site.search.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.exception.BusinessException;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/26
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public Page<Image> getImageByPage(ImageQueryRequest imageQueryRequest) {
        Page<Image> imagePage = new Page<>();
        if (imageQueryRequest == null) {
            return imagePage;
        }
        int current = imageQueryRequest.getCurrent();
        int pageSize = imageQueryRequest.getPageSize();
        String searchText = imageQueryRequest.getSearchText();
        int cursor = (current - 1) * pageSize;
        String url = "https://cn.bing.com/images/search?q=%s&form=IQFRML&first=%s";
        url = String.format(url, searchText, cursor);
        try {
            List<Image> images = new ArrayList<>();
            Document document = Jsoup.connect(url).get();
            Elements imageList = document.select("div[class=iuscp isv]");
            for (int i = 0; i < imageList.size(); i++) {
                Element imageElement = imageList.get(i);
                String imageUrl = imageElement.select("img[class=mimg vimgld]").attr("data-src");

                String imageTitle = imageElement.select("a[class=inflnk]").attr("aria-label");

                Image image = new Image();
                image.setUrl(imageUrl);
                image.setTitle(imageTitle);
                if (StringUtils.isNotBlank(imageUrl) && StringUtils.isNotBlank(imageTitle)){
                    images.add(image);
                }
                //根据页码大小限制
                if (images.size() >= pageSize) {
                    break;
                }
            }
            System.out.println(url);
            System.out.println(images);
            imagePage.setRecords(images);
            imagePage.setCurrent(current);
            imagePage.setSize(pageSize);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询图片失败");
        }
        return imagePage;
    }
}
