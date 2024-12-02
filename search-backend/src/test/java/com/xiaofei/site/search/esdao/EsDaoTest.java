package com.xiaofei.site.search.esdao;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaofei.site.search.mapper.PostMapper;
import com.xiaofei.site.search.model.dto.post.PostEsDTO;
import com.xiaofei.site.search.model.entity.Post;
import com.xiaofei.site.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/2
 */
@Slf4j
@SpringBootTest
public class EsDaoTest {

    @Resource
    private PostMapper postMapper;

    @Resource
    private PostEsDao postEsDao;

    @Test
    public void importData() {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("isDelete", 1);
        List<Post> postList = postMapper.selectList(queryWrapper);

        List<PostEsDTO> postEsDTOList = new ArrayList<>();

        if (CollUtil.isNotEmpty(postList)) {
            for (Post post : postList) {
                PostEsDTO postEsDTO = new PostEsDTO();
                BeanUtils.copyProperties(post, postEsDTO);
                postEsDTOList.add(postEsDTO);
            }
        }

        Iterable<PostEsDTO> postEsDTOS = postEsDao.saveAll(postEsDTOList);
        if (CollUtil.isNotEmpty(postEsDTOS)) {
            for (PostEsDTO postEsDTO : postEsDTOS) {
                log.info("插入成功 -> " + JSONUtil.toJsonStr(postEsDTO));
            }
        }

    }

    @Test
    public void findAll() {
        Iterable<PostEsDTO> all = postEsDao.findAll(PageRequest.of(0, 5, Sort.by("createTime")));
        if (CollUtil.isNotEmpty(all)) {
            for (PostEsDTO postEsDTO : all) {
                log.info("数据：->" + postEsDTO);
            }
        }
    }

    @Test
    public void queryById(){
        Optional<PostEsDTO> byId = postEsDao.findById(1861062275553792001L);
        PostEsDTO postEsDTO = byId.get();
        log.info("数据：->" + postEsDTO);
    }

    @Test
    public void delete(){
        postEsDao.deleteById(1861062275553792001L);
    }

    @Test
    public void update(){
        
    }
}
