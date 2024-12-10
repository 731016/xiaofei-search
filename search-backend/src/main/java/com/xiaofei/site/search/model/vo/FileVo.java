package com.xiaofei.site.search.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Data
public class FileVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小（格式化后的），如：1.5MB
     */
    private String fileSizeFormat;

    private String fileExtension;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 上传用户名
     */
    private Long userId;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 文件预览URL
     */
    private String previewUrl;

    /**
     * 文件下载URL
     */
    private String downloadUrl;

    /**
     * 是否可预览
     */
    private Boolean canPreview;

    /**
     * 是否可下载
     */
    private Boolean canDownload;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 业务
     */
    private String biz;

    private String content;  // 新增字段，用于存储base64内容
}