package com.xiaofei.site.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.datasource.*;
import com.xiaofei.site.search.model.dto.search.SearchQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.model.enums.SearchTypeEnum;
import com.xiaofei.site.search.model.vo.PostVO;
import com.xiaofei.site.search.model.vo.SearchVo;
import com.xiaofei.site.search.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author tuaofei
 * @description 查询-门面模式
 * @date 2024/11/29
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return searchVo;
        }
        String searchText = searchQueryRequest.getSearchText();
        String searchType = searchQueryRequest.getSearchType();
        int current = searchQueryRequest.getCurrent();
        int pageSize = searchQueryRequest.getPageSize();
        if (StringUtils.isBlank(searchType)) {
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource postDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.POST.getValue());
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource userDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.USER.getValue());
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<Image>> imageTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource imageDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.IMAGE.getValue());
                Page<Image> imagePage = imageDataSource.doSearch(searchText, current, pageSize);
                return imagePage;
            }, threadPoolTaskExecutor);

            CompletableFuture.allOf(postTask, userTask, imageTask);

            try {
                Page<PostVO> postVOPage = postTask.get();
                searchVo.setPostList(postVOPage.getRecords());
                Page<UserVO> userVOPage = userTask.get();
                searchVo.setUserList(userVOPage.getRecords());
                Page<Image> imagePage = imageTask.get();
                searchVo.setImageList(imagePage.getRecords());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            SearchDataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(searchType);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            List<?> records = page.getRecords();
            searchVo.setDataList(records);
        }
        return searchVo;
    }
}
