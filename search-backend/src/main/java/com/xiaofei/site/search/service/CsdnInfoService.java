package com.xiaofei.site.search.service;

import com.xiaofei.site.search.model.dto.csdn.CsdnInfoDto;

/**
 * @author tuaofei
 * @description TODO
 * @date 2025/1/15
 */
public interface CsdnInfoService {

    /**
     * 根据个人中心id查询CSDN信息
     * @param userId
     * @return
     */
    CsdnInfoDto searchCsdnInfo(String userId);
}
