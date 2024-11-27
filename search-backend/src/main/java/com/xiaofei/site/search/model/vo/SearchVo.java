package com.xiaofei.site.search.model.vo;

import com.xiaofei.site.search.model.entity.Image;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/27
 */
@Data
public class SearchVo implements Serializable {

    private List<PostVO> postList;
    private List<Image> imageList;
    private List<UserVO> userList;
}
