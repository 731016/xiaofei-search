package com.xiaofei.site.search.model.dto.file;

import com.xiaofei.site.search.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 */
@Data
public class FileQueryRequest extends PageRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private String fileName;

    private String content;

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}