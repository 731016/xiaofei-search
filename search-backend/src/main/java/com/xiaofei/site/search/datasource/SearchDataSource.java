package com.xiaofei.site.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tuaofei
 * @description 查询数据源
 * @date 2024/11/29
 */
public interface SearchDataSource<T> {

    /**
     * 通用查询接口
     * @param searchText
     * @param current
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, int current, int pageSize);
}
