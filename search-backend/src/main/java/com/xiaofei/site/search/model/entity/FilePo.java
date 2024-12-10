package com.xiaofei.site.search.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Data
@TableName("file")
public class FilePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 存储路径（相对路径）
     */
    private String filePath;

    /**
     * 文件类型/MIME类型，如 image/jpeg
     */
    private String fileType;

    /**
     * 文件扩展名，如 .jpg
     */
    private String fileExtension;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件MD5值，用于文件去重
     */
    private String md5;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 业务
     */
    private String biz;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 文件内容
     */
    @TableField(value = "content", jdbcType = JdbcType.LONGVARCHAR)
    private String content;
}
