package com.xiaofei.site.search;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xiaofei.site.search.model.dto.post.PostAddRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.model.entity.Post;
import com.xiaofei.site.search.model.entity.User;
import com.xiaofei.site.search.service.PostService;
import com.xiaofei.site.search.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;


    @Test
    public void textPostImport() {
        String url = "http://xiaofei.site/";
        try {
            Document document = Jsoup.connect(url).get();
            Elements postElement = document.select("ul[id=post-cols]");
            Elements postLi = postElement.select("li[class=article-container]");

            for (int i = 0; i < postLi.size(); i++) {

                PostAddRequest postAddRequest = new PostAddRequest();

                Element li = postLi.get(i);

                Elements postImg = li.select("div[class=article-thumbnail]");
                for (Element img : postImg) {
                    String imgStr = img.select("img").attr("src");
                    String imgUrl = url + imgStr;
                }

                Elements postTags = li.select("div[class=article-content] > span");
                List<String> tags = new ArrayList<>();
                for (Element tag : postTags) {
                    String tagStr = tag.select("em").text();
                    String[] tagArr = tagStr.split("#");
                    for (int j = 0; j < tagArr.length; j++) {
                        String tagTrim = tagArr[j].trim();
                        if (StringUtils.isNotBlank(tagTrim)) {
                            tags.add(tagTrim);
                        }
                    }
                }
                postAddRequest.setTags(tags);

                Elements postTitle = li.select("h1[data-dia=article-link]");
                String title = postTitle.text();
                postAddRequest.setTitle(title);

                Elements postContent = li.select("p[style=overflow: hidden;max-height: 6rem;]");
                String content = postContent.text();
                postAddRequest.setContent(content);

                Post post = new Post();
                BeanUtils.copyProperties(postAddRequest, post);
                List<String> tagList = postAddRequest.getTags();
                if (tagList != null) {
                    post.setTags(JSONUtil.toJsonStr(tagList));
                }
                postService.validPost(post, true);
                post.setUserId(1858872909540597761L);
                post.setFavourNum(0);
                post.setThumbNum(0);
                boolean result = postService.save(post);
                System.out.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void imageImport(){
        String url = "https://cn.bing.com/images/search?q=%E5%9B%BE%E7%89%87&form=IQFRML&first=100";
        try {
            List<Image> images = new ArrayList<>();
            Document document = Jsoup.connect(url).get();
            Elements imageList = document.select("div[class=iuscp isv]");
            for (int i = 0; i < imageList.size(); i++) {
                Element imageElement = imageList.get(i);
                String imageUrl = imageElement.select("img[class=mimg vimgld]").attr("data-src");

                String imageTitle = imageElement.select("a[class=inflnk]").attr("aria-label");

                Image image = new Image();
                image.setUrl(imageUrl);
                image.setTitle(imageTitle);
                System.out.println(image);
                images.add(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
