package com.xiaofei.site.search.controller;

import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.model.dto.csdn.CsdnInfoDto;
import com.xiaofei.site.search.service.CsdnInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tuaofei
 * @description TODO
 * @date 2025/1/15
 */
@RestController
@RequestMapping("/csdn")
@Slf4j
public class CsdnController {

    @Resource
    private CsdnInfoService csdnInfoService;

    @GetMapping("/searchCsdnInfo")
    public BaseResponse<CsdnInfoDto> searchCsdnInfo(@RequestParam(required = false) String userId) {
        CsdnInfoDto csdnInfoDto = csdnInfoService.searchCsdnInfo(userId);
        return ResultUtils.success(csdnInfoDto);
    }

    @GetMapping("/searchCsdnTotalVisitsNum")
    public String searchCsdnTotalVisitsNum(String userId) {
        CsdnInfoDto csdnInfoDto = csdnInfoService.searchCsdnInfo(userId);
        if (csdnInfoDto != null) {
            return csdnInfoDto.getTotalVisitsNum();
        }
        return null;
    }
}
