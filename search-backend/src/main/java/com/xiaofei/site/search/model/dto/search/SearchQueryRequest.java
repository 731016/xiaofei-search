package com.xiaofei.site.search.model.dto.search;

import com.xiaofei.site.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 聚合查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 查询分类
     */
    private String searchType;


    private static final long serialVersionUID = 1L;
}