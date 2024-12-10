package com.xiaofei.site.search.model.dto.file;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件 ES 包装类
 **/
@Document(indexName = "file_v3")
@Data
public class FileEsDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 文件名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String fileName;

    /**
     * 文件类型
     */
    @Field(type = FieldType.Keyword)
    private String fileType;

    /**
     * 解析后的文本内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 文件描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String description;

    /**
     * 上传用户ID
     */
    @Field(type = FieldType.Long)
    private Long userId;

    /**
     * 业务类型
     */
    @Field(type = FieldType.Keyword)
    private String biz;

    /**
     * 下载次数
     */
    @Field(type = FieldType.Integer)
    private Integer downloadCount;

    /**
     * 创建时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updateTime;

    private Integer isDelete;


    private static final long serialVersionUID = 1L;
}