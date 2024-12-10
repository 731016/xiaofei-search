package com.xiaofei.site.search.model.dto.file;

import com.xiaofei.site.search.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class FileDownloadRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private Long id;

    private static final long serialVersionUID = 1L;
}