package com.xiaofei.site.search.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.config.CsdnConfig;
import com.xiaofei.site.search.exception.BusinessException;
import com.xiaofei.site.search.model.dto.csdn.CsdnInfoDto;
import com.xiaofei.site.search.service.CsdnInfoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuaofei
 * @description TODO
 * @date 2025/1/15
 */
@Service
public class CsdnInfoServiceImpl implements CsdnInfoService {

    @Resource
    private CsdnConfig csdnConfig;

    @Override
    public CsdnInfoDto searchCsdnInfo(String userId) {
        String url = csdnConfig.getUrl();
        String user = userId;
        if (StrUtil.isBlank(userId)) {
            user = csdnConfig.getDefaultUser();
        }

        String reqUrl = String.format("%s%s", url, user);

        try {
            Document document = Jsoup.connect(reqUrl).get();
            Elements info = document.select("div[class=user-profile-head-info-r-c]");
            List<String> nameList = new ArrayList<>();
            List<String> numList = new ArrayList<>();
            if (info != null) {
                Elements name = info.select("div[class=user-profile-statistics-name]");
                if (name != null) {
                    for (int i = 0; i < name.size(); i++) {
                        nameList.add(name.get(i).text());
                    }
                }
                Elements num = info.select("div[class=user-profile-statistics-num]");
                if (num != null) {
                    for (int i = 0; i < num.size(); i++) {
                        numList.add(num.get(i).text());
                    }
                }
            }
            if (CollectionUtil.isNotEmpty(nameList) && CollectionUtil.isNotEmpty(numList)) {
                CsdnInfoDto csdnInfoDto = new CsdnInfoDto();
                for (int i = 0; i < nameList.size(); i++) {
                    String name = nameList.get(i);
                    String num = numList.get(i);
                    if (name.contains("总访问量")){
                        csdnInfoDto.setTotalVisitsNum(num);
                    }
                    if (name.contains("原创")){
                        csdnInfoDto.setOriginalNum(num);
                    }
                    if (name.contains("排名")){
                        csdnInfoDto.setRankingNum(num);
                    }
                    if (name.contains("粉丝")){
                        csdnInfoDto.setFansNum(num);
                    }
                    if (name.contains("铁粉")){
                        csdnInfoDto.setDieHardFansNum(num);
                    }
                }
                return csdnInfoDto;
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取csdn信息失败");
        }
        return null;
    }
}
