package com.xiaofei.site.search.esdao;

import com.xiaofei.site.search.model.dto.post.PostEsDTO;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);

    List<PostEsDTO> findByTitle(String title);

    List<PostEsDTO> findByContent(String content);

    List<PostEsDTO> findByTags(List<String> tags);


}