package com.xiaofei.site.search.aop;

import com.xiaofei.site.search.config.FileConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Component
public class FileUploadInterceptor implements HandlerInterceptor {

    @Resource
    private FileConfig fileConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");

            // 检查文件大小
            if (file.getSize() > fileConfig.getMaxSize()) {
                throw new RuntimeException("File too large");
            }

            // 检查文件类型
            if (!fileConfig.getAllowedTypes().contains(file.getContentType())) {
                throw new RuntimeException("File type not allowed");
            }
        }
        return true;
    }
}