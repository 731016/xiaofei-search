package com.xiaofei.site.search.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.annotation.AuthCheck;
import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.DeleteRequest;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.constant.UserConstant;
import com.xiaofei.site.search.exception.BusinessException;
import com.xiaofei.site.search.exception.ThrowUtils;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.dto.post.PostAddRequest;
import com.xiaofei.site.search.model.dto.post.PostEditRequest;
import com.xiaofei.site.search.model.dto.post.PostQueryRequest;
import com.xiaofei.site.search.model.dto.post.PostUpdateRequest;
import com.xiaofei.site.search.model.dto.search.SearchQueryRequest;
import com.xiaofei.site.search.model.dto.user.UserQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.model.entity.Post;
import com.xiaofei.site.search.model.entity.User;
import com.xiaofei.site.search.model.vo.PostVO;
import com.xiaofei.site.search.model.vo.SearchVo;
import com.xiaofei.site.search.model.vo.UserVO;
import com.xiaofei.site.search.service.ImageService;
import com.xiaofei.site.search.service.PostService;
import com.xiaofei.site.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 聚合查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return ResultUtils.success(searchVo);
        }
        String searchText = searchQueryRequest.getSearchText();

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postService.listPostVoPage(postQueryRequest, httpServletRequest);
            return postVOPage;
        }, threadPoolTaskExecutor);

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userService.listUserVoPage(userQueryRequest);
            return userVOPage;
        }, threadPoolTaskExecutor);

        CompletableFuture<Page<Image>> imageTask = CompletableFuture.supplyAsync(() -> {
            ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
            imageQueryRequest.setSearchText(searchText);
            Page<Image> imagePage = imageService.getImageByPage(imageQueryRequest);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ResultUtils.success(searchVo);


//        PostQueryRequest postQueryRequest = new PostQueryRequest();
//        postQueryRequest.setSearchText(searchText);
//        Page<PostVO> postVOPage = postService.listPostVoPage(postQueryRequest, httpServletRequest);
//        searchVo.setPostList(postVOPage.getRecords());
//
//        UserQueryRequest userQueryRequest = new UserQueryRequest();
//        userQueryRequest.setUserName(searchText);
//        Page<UserVO> userVOPage = userService.listUserVoPage(userQueryRequest);
//        searchVo.setUserList(userVOPage.getRecords());
//
//        ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
//        imageQueryRequest.setSearchText(searchText);
//        Page<Image> imagePage = imageService.getImageByPage(imageQueryRequest);
//        searchVo.setImageList(imagePage.getRecords());
//
//        return ResultUtils.success(searchVo);
    }

}
