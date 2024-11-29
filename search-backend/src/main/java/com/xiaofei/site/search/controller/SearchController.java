package com.xiaofei.site.search.controller;

import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.model.dto.search.SearchQueryRequest;
import com.xiaofei.site.search.model.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 聚合查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = searchFacade.searchAll(searchQueryRequest, httpServletRequest);
        return ResultUtils.success(searchVo);
    }

}
