package com.xiaofei.site.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileConfig {

    /**
     * 上传根路径
     */
    private String path;

    /**
     * 最大文件大小（字节）
     */
    private Long maxSize;

    /**
     * 允许的文件类型列表
     */
    private List<String> allowedTypes;
}
