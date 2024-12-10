package com.xiaofei.site.search.datasource;

import com.xiaofei.site.search.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/29
 */
@Component
public class DataSourceRegistry {

    @Resource
    private ImageDataSource imageDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private FileDataSource fileDataSource;

    private Map<String, SearchDataSource<T>> dataSourceMap;

    /**
     * 在依赖注入完成后，执行
     */
    @PostConstruct
    public void doInit() {
        dataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.IMAGE.getValue(), imageDataSource);
            put(SearchTypeEnum.FILE.getValue(), fileDataSource);
        }};
    }

    public SearchDataSource getDataSourceByType(String searchType) {
        if (dataSourceMap == null) {
            return null;
        }
        return dataSourceMap.get(searchType);
    }


}
