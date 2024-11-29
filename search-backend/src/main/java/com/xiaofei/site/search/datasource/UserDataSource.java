package com.xiaofei.site.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.model.dto.user.UserQueryRequest;
import com.xiaofei.site.search.model.vo.UserVO;
import com.xiaofei.site.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 */
@Service
@Slf4j
public class UserDataSource implements SearchDataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, int current, int pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVoPage(userQueryRequest);
        return userVOPage;
    }
}
