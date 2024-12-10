# 聚合搜索平台

## 1.项目地址

编程导航：https://www.codefather.cn/course/1790979621621641217

项目地址：https://github.com/731016/xiaofei-search



## 2.项目介绍

用户：允许用户在同一个页面集中搜索出不同来源、不同类型的内容，提升用户的检索效率和搜索体验

企业：有多个项目的数据需要被搜索时，无需针对每个项目单独开发搜索功能，可直接将数据接入搜索中台，提升开发效率



## 3.主要技术栈

前端
vue3,ant design vue,页面状态同步

后端
springboot
elastic stack
数据抓取(httpclient,jsoup)
设计模式(门面,适配器,注册器)
数据同步(定时,双写,logstach,canal)
Jmeter压力测试

## 4.前端初始化

[快速上手 - Ant Design Vue](https://www.antdv.com/docs/vue/getting-started-cn)

```cmd
npm install -g @vue/cli

vue create search-backend
```

选择自定义：Manually select features

使用TS和路由

![image-20241118222754137](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118222754137.png)

![image-20241118222955723](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118222955723.png)

![image-20241118223019249](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118223019249.png)

路由使用hash模式

![image-20241118223407526](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118223407526.png)

代码规范

![image-20241118223419392](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118223419392.png)

保存时校验代码是符合规范

![image-20241118223430047](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118223430047.png)

![image-20241118223440319](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118223440319.png)

![image-20241118223451704](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241118223451704.png)



### 使用组件

```cmd
$ npm i --save ant-design-vue@4.x
```

#### 注册

修改`main.ts`

```ts
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import Antd from "ant-design-vue";
import "ant-design-vue/dist/reset.css";

createApp(App).use(Antd).use(router).mount("#app");
```

如果报红，webstrom设置代码规范检查，`ctrl+alt+L`生效

![image-20241119214121550](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241119214121550.png)

#### 运行

![image-20241119214440817](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241119214440817.png)

正常运行

![image-20241119214458054](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241119214458054.png)



## 5.后端初始化

初始化项目代码：https://wwzp.lanzouv.com/iTUQW2fk0jvi

修改数据库连接

执行create_table.sql创建数据表

可直接启动MainApplication

访问http://localhost:8101/api/doc.html



分别执行一下5个接口，初始化一下数据

用户注册，用户登录，增加帖子，根据id获取帖子，收藏帖子

![image-20241119222043841](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241119222043841.png)



## 6.开发前端搜索页面

删除views初始页面AboutView



![image-20241120203443708](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120203443708.png)

删除router相关配置

![image-20241120204510494](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120204510494.png)

修改路由页面信息

![image-20241120203822596](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120203822596.png)

删除初始信息

![image-20241120204430283](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120204430283.png)

修改首页

![image-20241120204521394](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120204521394.png)

### 搜索框

![image-20241120210150926](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120210150926.png)

![image-20241120210135835](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120210135835.png)

### Tab切换

![image-20241120210358241](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120210358241.png)

![image-20241120210448899](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120210448899.png)



### 列表展示

```vue
<template>图片列表</template>

<script setup lang="ts"></script>

<style scoped></style>
```

```vue
<template>文章列表</template>

<script setup lang="ts"></script>

<style scoped></style>
```

```vue
<template>用户列表</template>

<script></script>

<style scoped></style>
```

分隔符组件

```vue
<template>
  <div class="my-divider"></div>
</template>

<script setup lang="ts"></script>

<style scoped>
.my-divider {
  margin-bottom: 16px;
}
</style>
```

主页使用组件

```vue
<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchText"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <MyDivider />
    <a-tabs v-model:activeKey="activeKey">
      <a-tab-pane key="post" tab="帖子">
        <PostList />
      </a-tab-pane>
      <a-tab-pane key="image" tab="用户">
        <UserList />
      </a-tab-pane>
      <a-tab-pane key="user" tab="图片">
        <ImageList />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import ImageList from "@/components/ImageList.vue";
import MyDivider from "@/components/MyDivider.vue";

const searchText = ref("");
const activeKey = ref("post");

const onSearch = (value: string) => {
  alert(value);
};
</script>
```

样式修改

```vue
<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style>
#app {
  ...
  padding: 20px;
  max-width: 1920px;
  margin: 0 auto;
}

...
</style>
```

## 7.记录搜索状态

使用`url`记录用户搜索参数,刷新时还能还原之前的搜索状态

建议:通过url改变页面状态,单向改变

```java
使用route(query.text,params.category)和watchEffect/watch监听路由改变
改变url地址(点击搜索框,搜索内容填充到url上,切换tab或者其他分页...也要记录)
url改变时,改变页面状态
    
整体逻辑：用户操作（点击搜索框,搜索内容填充到url上,切换tab或者其他分页）-> 更新通用查询参数searchParams -> 更新路由 -> 触发查询
```

动态路由设置

```ts
...
const routes: Array<RouteRecordRaw> = [
  ...
  {
    path: "/:category",
    name: "index",
    component: IndexPage,
  },
];
...
```

随便输入地址还是保持页面不变

![image-20241120221522410](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120221522410.png)



点击搜索框,搜索内容填充到url上

```vue
const onSearch = (value: string) => {
  alert(value);
  router.push({
    query: {
      text: value,
    },
  });
};
```

切换tab或者其他分页

```vue
const onTabChange = (activeKey: string) => {
  router.push({
    path: `/${activeKey}`,
    query: {
      text: searchText,
    },
  });
};
```

```vue
<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <MyDivider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="帖子">
        <PostList />
      </a-tab-pane>
      <a-tab-pane key="image" tab="图片">
        <ImageList />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const activeKey = ref("post");
const router = useRouter();

/**
 * url查询关键字
 */
const searchParams = ref({
  text: "",
});

/**
 * 查询
 * @param value
 */
const onSearch = (value: string) => {
  alert(value);
  router.push({
    query: searchParams.value,
  });
};

/**
 * tab切换
 * @param activeKey
 */
const onTabChange = (activeKey: string) => {
  router.push({
    path: `/${activeKey}`,
    query: searchParams.value,
  });
};
</script>
```

切换tab正常设置url

![image-20241120222648800](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120222648800.png)

![image-20241120222655497](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241120222655497.png)

如果刷新，分类会丢失，没有保存分类数据

```vue
<script setup lang="ts">
import { ref, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";

const router = useRouter();
const route = useRoute();
/**
 * 当前激活tab
 */
const activeKey = route.params.category;

/**
 * 页面初始化查询参数
 */
const initSearchParams = {
  text: "",
  pageNum: 1,
  pageSize: 10,
};

/**
 * url查询关键字
 */
const searchParams = ref(initSearchParams);

/**
 * 监听url改变
 * 更新查询框参数
 */
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
  } as any;
});
</script>
```



## 8.axios请求接入

[Axios中文文档 | Axios中文网](https://www.axios-http.cn/)

全局请求配置`ReqAxios.ts`

```ts
import axios from "axios";

const reqAxios = axios.create({
  baseURL: "http://localhost:8101/api",
  timeout: 10000,
  headers: {},
});

// 请求拦截
reqAxios.interceptors.request.use(
  (config) => {
    // let token = localStorage.getItem("token") || "";
    // config.headers["Authorization"] = token;
    return config;
  },
  (err) => {
    Promise.reject(err);
  }
);

// 添加响应拦截器
reqAxios.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    const data = response.data;
    if (data.code === 0) {
      return data.data;
    }
    return data;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    if (error && error.response) {
      error.message = "系统错误" + error.response.status;
    } else {
      error.message = "连接服务器失败!";
    }
    return Promise.reject(error);
  }
);
/**
 * 使用es6的export default导出了一个函数，导出的函数代替axios去帮我们请求数据，
 * 函数的参数及返回值如下：
 * @param {String} method  请求的方法：get、post、delete、put
 * @param {String} url     请求的url:
 * @param {Object} data    请求的参数
 * @returns {Promise}     返回一个promise对象，其实就相当于axios请求数据的返回值
 */
export default function (method: string, url: string, data: any) {
  method = method.toLowerCase();
  if (method == "post") {
    return reqAxios.post(url, data);
  } else if (method == "get") {
    return reqAxios.get(url, { params: data });
  } else {
    console.error("未知的method" + method);
    return false;
  }
}
```

请求方法`Search.ts`

```ts
import ReqAxios from "@/plugins/ReqAxios";

/**
 * 分页获取列表（封装类）
 * @param params
 */
export const listPostVOByPage = (params: any) =>
  ReqAxios("post", "/post/list/page/vo", params);
```

请求代码

```ts
/**
 * 查询结果
 */
const searchResultList = ref([]);

/**
 * 查询
 * @param value
 */
const onSearch = (value: string) => {
  router.push(searchParams.value);
  listPostVOByPage({})
    .then((data) => {
      console.log(data.records);
      searchResultList.value = data.records;
    })
    .catch((error) => {
      console.error(error);
    });
};
```

### 展示数据页面优化

去掉app.vue初始样式

```vue
<style>
#app {
  padding: 20px;
  max-width: 1920px;
  margin: 0 auto;
}
    ...
```



帖子页面

```vue
<template>
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta :description="item.content">
          <template #title>
            <a href="https://www.antdv.com/">{{ item.title }}</a>
          </template>
          <template #avatar>
            <a-avatar src="https://joeschmoe.io/api/v1/random" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  postList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});
</script>

<style scoped></style>
```

> 1. `import { withDefaults, defineProps } from "vue";`：从 Vue 库中导入了 `withDefaults` 和 `defineProps` 函数。
> 2. `interface Props { postList: any[]; }`：定义了一个 TypeScript 接口 `Props`，它包含一个属性 `postList`，这是一个任意类型的数组。
> 3. `const props = withDefaults(defineProps<Props>(), { postList: () => [] });`：这里首先使用 `defineProps` 函数定义了组件的 props，然后使用 `withDefaults` 函数为这些 props 提供默认值。如果父组件没有传递 `postList` 属性，那么它将默认为一个空数组。

图片页面

```vue
<template>
  <a-list item-layout="horizontal" :data-source="props.imageList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-image :width="200" :src="item.userAvatar" />
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  imageList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  imageList: () => [],
});
</script>

<style scoped></style>
```

用户列表

```vue
<template>
  <a-list item-layout="horizontal" :data-source="props.userList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card hoverable style="width: 240px" :data-source="props.userList">
          <template #cover>
            <img alt="example" :src="item.userAvatar" />
          </template>
          <a-card-meta :title="item.userName">
            <template #description>{{ item.userProfile }}</template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  userList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  userList: () => [],
});
</script>

<style scoped></style>
```

主页

```vue
<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <MyDivider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="帖子">
        <PostList :postList="searchResultPostList" />
      </a-tab-pane>
      <a-tab-pane key="image" tab="图片">
        <ImageList :imageList="searchResultImageList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :userList="searchResultUserList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { listPostVOByPage, listUserVOByPage } from "@/request/Search";
import PostList from "@/components/PostList.vue";
import ImageList from "@/components/ImageList.vue";
import UserList from "@/components/UserList.vue";

const router = useRouter();
const route = useRoute();
/**
 * 当前激活tab
 */
let activeTabKey = route.params.category;

/**
 * 页面初始化查询参数
 */
const initSearchParams = {
  text: "",
  current: 1,
  pageSize: 10,
};

/**
 * url查询关键字
 */
const searchParams = ref(initSearchParams);

/**
 * 监听url改变
 * 更新查询框参数
 */
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
  } as any;
});

/**
 * 查询结果
 */
const searchResultPostList = ref([]);
const searchResultImageList = ref([]);
const searchResultUserList = ref([]);

/**
 * 查询
 * @param value
 */
const onSearch = (value: string) => {
  router.push({
    query: searchParams.value,
  });
  listPostVOByPage({})
    .then((data: { records: never[] }) => {
      console.log(data.records);
      searchResultPostList.value = data.records;
    })
    .catch((error: any) => {
      console.error(error);
    });
  listUserVOByPage({})
    .then((data) => {
      console.log(data.records);
      searchResultUserList.value = data.records;
    })
    .catch((error) => {
      console.error(error);
    });
  listUserVOByPage({})
    .then((data) => {
      console.log(data.records);
      searchResultImageList.value = data.records;
    })
    .catch((error) => {
      console.error(error);
    });
  console.log(activeTabKey);
  // if ("post" === activeTabKey) {
  // }
  // if ("user" === activeTabKey) {
  // }
  // if ("image" === activeTabKey) {
  // }
};

/**
 * tab切换
 * @param activeKey
 */
const onTabChange = (activeKey: string) => {
  router.push({
    path: `/${activeKey}`,
    query: searchParams.value,
  });
  activeTabKey = activeKey;
};

onMounted(() => {
  // onTabChange("post");
});
</script>
```



## 9.数据抓取

### 1.文章

从请求地址请求数据,解析对应的属性

```java
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
```



### 2.图片

jsoup 解析网页,获取html解析图片地址

```java
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
```

图片没有现成的接口，需要新增



在`entity`包下，新增图片实体

```java
package com.xiaofei.site.search.model.entity;

import lombok.Data;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/26
 */
@Data
public class Image {

    private String title;

    private String url;
}
```

在`dto`包下新增`image`目录，添加图片查询请求信息

```java
package com.xiaofei.site.search.model.dto.image;

import com.xiaofei.site.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;


    private static final long serialVersionUID = 1L;
}
```

提供查询图片接口及实现类

```java
package com.xiaofei.site.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;

/**
 * 图片服务
 *
 */
public interface ImageService {


    /**
     * 分页获取图片
     *
     * @return
     */
    Page<Image> getImageByPage(ImageQueryRequest imageQueryRequest);
}
package com.xiaofei.site.search.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.exception.BusinessException;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/26
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public Page<Image> getImageByPage(ImageQueryRequest imageQueryRequest) {
        Page<Image> imagePage = new Page<>();
        if (imageQueryRequest == null) {
            return imagePage;
        }
        int current = imageQueryRequest.getCurrent();
        int pageSize = imageQueryRequest.getPageSize();
        String searchText = imageQueryRequest.getSearchText();
        int cursor = (current - 1) * pageSize;
        String url = "https://cn.bing.com/images/search?q=%s&form=IQFRML&first=%s";
        url = String.format(url, searchText, cursor);
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
                if (StringUtils.isNotBlank(imageUrl) && StringUtils.isNotBlank(imageTitle)){
                    images.add(image);
                }
                //根据页码大小限制
                if (images.size() >= pageSize) {
                    break;
                }
            }
            System.out.println(url);
            System.out.println(images);
            imagePage.setRecords(images);
            imagePage.setCurrent(current);
            imagePage.setSize(pageSize);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询图片失败");
        }
        return imagePage;
    }
}
```

在`controller`包下新增控制层ImageController

```java
package com.xiaofei.site.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.ErrorCode;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.exception.ThrowUtils;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 *
 */
@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Resource
    private ImageService imageService;

    /**
     * 分页获取列表（封装类）
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Image>> listPostByPage(@RequestBody ImageQueryRequest imageQueryRequest,
                                                    HttpServletRequest request) {
        long current = imageQueryRequest.getCurrent();
        long size = imageQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Image> imagePage = imageService.getImageByPage(imageQueryRequest);
        return ResultUtils.success(imagePage);
    }

}
```

前端图片页面完善

`Search.ts`，增加查询图片信息接口

```ts
import ReqAxios from "@/plugins/ReqAxios";

/**
 * 分页获取列表（封装类）
 * @param params
 */
export const listPostVOByPage = (params: any) =>
  ReqAxios("post", "/post/list/page/vo", params);

/**
 * 分页获取用户封装列表
 * @param params
 */
export const listUserVOByPage = (params: any) =>
  ReqAxios("post", "/user/list/page/vo", params);

/**
 * 分页获取图片
 * @param params
 */
export const listImageByPage = (params: any) =>
  ReqAxios("post", "/image/list/page/vo", params);

```



`IndexPage.vue`，完善查询图片信息

```vue
<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.searchText"
      placeholder="input search searchText"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <MyDivider />
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="帖子">
        <PostList :postList="searchResultPostList" />
      </a-tab-pane>
      <a-tab-pane key="image" tab="图片">
        <ImageList :imageList="searchResultImageList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :userList="searchResultUserList" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  listImageByPage,
  listPostVOByPage,
  listUserVOByPage,
} from "@/request/Search";
import PostList from "@/components/PostList.vue";
import ImageList from "@/components/ImageList.vue";
import UserList from "@/components/UserList.vue";

const router = useRouter();
const route = useRoute();
/**
 * 当前激活tab
 */
let activeTabKey = route.params.category;

/**
 * 页面初始化查询参数
 */
const initSearchParams = {
  searchText: "",
  current: 1,
  pageSize: 10,
};

/**
 * url查询关键字
 */
const searchParams = ref(initSearchParams);

/**
 * 监听url改变
 * 更新查询框参数
 */
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    searchText: route.query.searchText,
  } as any;
});

/**
 * 查询结果
 */
const searchResultPostList = ref([]);
const searchResultImageList = ref([]);
const searchResultUserList = ref([]);

/**
 * 查询
 * @param value
 */
const onSearch = (value: string) => {
  router.push({
    query: searchParams.value,
  });
  searchAll(activeTabKey);
  // if ("post" === activeTabKey) {
  // }
  // if ("user" === activeTabKey) {
  // }
  // if ("image" === activeTabKey) {
  // }
};

const searchAll = (activeTabKey: string) => {
  listPostVOByPage(searchParams.value)
    .then((data: { records: never[] }) => {
      console.log(data.records);
      searchResultPostList.value = data.records;
    })
    .catch((error: any) => {
      console.error(error);
    });
  listUserVOByPage(searchParams.value)
    .then((data) => {
      console.log(data.records);
      searchResultUserList.value = data.records;
    })
    .catch((error) => {
      console.error(error);
    });
  listImageByPage(searchParams.value)
    .then((data) => {
      console.log(data.records);
      searchResultImageList.value = data.records;
    })
    .catch((error) => {
      console.error(error);
    });
};

/**
 * tab切换
 * @param activeKey
 */
const onTabChange = (activeKey: string) => {
  router.push({
    path: `/${activeKey}`,
    query: searchParams.value,
  });
  activeTabKey = activeKey;
};

onMounted(() => {
  // onTabChange("post");
});
</script>
```



`ImageList.vue`，图片展示在一列

```vue
<template>
  <a-list
    item-layout="horizontal"
    :grid="{ gutter: 16, column: 5 }"
    :data-source="props.imageList"
  >
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card hoverable>
          <template #cover>
            <a-image :src="item.url" :alt="item.title" />
          </template>
          <a-card :title="item.title"></a-card>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  imageList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  imageList: () => [],
});
</script>

<style scoped></style>
```

![image-20241126224602371](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241126224602371.png)



## 10.搜索请求聚合

（1）切换不同的tab再发起单独分类的请求

（2）一次请求所有分类数据

（3）请求当前默认分类的数据和其它分类的数据总量

根据业务具体使用对应的方式



当前项目使用第2种方法



问题：

+ 请求过多
+ 请求参数不一致
+ 前端调用请求重复



### 后端修改

在modle.vo包下，新建通用返回结果`SearchVo`

```java
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
```

在modle.dto包下新建search目录，新增通用查询请求实体`SearchQueryRequest`

```java
package com.xiaofei.site.search.model.dto.search;

import com.xiaofei.site.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 聚合查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;


    private static final long serialVersionUID = 1L;
}
```

在controller包下，新增通用查询控制层`SearchController`

```java
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null){
            return ResultUtils.success(searchVo);
        }
        String searchText = searchQueryRequest.getSearchText();

        

        return ResultUtils.success(searchVo);
    }

}
```

封装帖子接口查询方法

在PostService，新增listPostVoPage

```java
 /**
     * 分页查询帖子
     * @param postQueryRequest
     * @param httpServletRequest
     * @return
     */
    Page<PostVO> listPostVoPage(PostQueryRequest postQueryRequest,HttpServletRequest httpServletRequest);

@Override
    public Page<PostVO> listPostVoPage(PostQueryRequest postQueryRequest, HttpServletRequest httpServletRequest) {
        int current = postQueryRequest.getCurrent();
        int pageSize = postQueryRequest.getPageSize();
        Page<Post> postPage = this.page(new Page<>(current, pageSize),
                this.getQueryWrapper(postQueryRequest));
        Page<PostVO> postVOPage = this.getPostVOPage(postPage, httpServletRequest);
        return postVOPage;
    }
```



封装用户接口查询方法

在UserService，新增listUserVoPage

```java
/**
     * 分页查询用户
     * @param userQueryRequest
     * @return
     */
    Page<UserVO> listUserVoPage(UserQueryRequest userQueryRequest);

@Override
    public Page<UserVO> listUserVoPage(UserQueryRequest userQueryRequest) {
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = this.page(new Page<>(current, pageSize),
                this.getQueryWrapper(userQueryRequest));
        List<UserVO> userVOList = this.getUserVO(userPage.getRecords());
        Page<UserVO> userVoPage = new Page<>(current, pageSize);
        userVoPage.setRecords(userVOList);
        return userVoPage;
    }
```

整合后的controller接口

```java
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
```

### 修改前端

在seach.ts中添加接口

```ts
/**
 * 聚合查询
 * @param params
 */
export const searchAll = (params: any) =>
  ReqAxios("post", "/search/all", params);
```

修改indexpage.vue

```vue
/**
 * 查询
 * @param value
 */
const onSearch = (value: string) => {
  router.push({
    query: searchParams.value,
  });
  loadAll();
};

const loadAll = () => {
  searchAll(searchParams.value)
    .then((data) => {
      console.log(data);
      searchResultPostList.value = data.postList;
      searchResultImageList.value = data.imageList;
      searchResultUserList.value = data.userList;
    })
    .catch((error) => {
      console.error(error);
    });
};
```



### 设计模式

#### 门面模式

适用场景：**如果你需要一个指向复杂子系统的直接接口， 且该接口的功能有限， 则可以使用外观模式。**

![image-20241129210823339](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241129210823339.png)

不用关心后面的查询具体操作

```java
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
```

复杂查询逻辑放在SearchFacade里面

```java
package com.xiaofei.site.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.common.BaseResponse;
import com.xiaofei.site.search.common.ResultUtils;
import com.xiaofei.site.search.model.dto.image.ImageQueryRequest;
import com.xiaofei.site.search.model.dto.post.PostQueryRequest;
import com.xiaofei.site.search.model.dto.search.SearchQueryRequest;
import com.xiaofei.site.search.model.dto.user.UserQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.model.enums.SearchTypeEnum;
import com.xiaofei.site.search.model.vo.PostVO;
import com.xiaofei.site.search.model.vo.SearchVo;
import com.xiaofei.site.search.model.vo.UserVO;
import com.xiaofei.site.search.service.ImageService;
import com.xiaofei.site.search.service.PostService;
import com.xiaofei.site.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author tuaofei
 * @description 查询-门面模式
 * @date 2024/11/29
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return searchVo;
        }
        String searchText = searchQueryRequest.getSearchText();
        String searchType = searchQueryRequest.getSearchType();
        if (StringUtils.isBlank(searchType)) {
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);
            switch (searchTypeEnum) {
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postService.listPostVoPage(postQueryRequest, httpServletRequest);
                    searchVo.setPostList(postVOPage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.listUserVoPage(userQueryRequest);
                    searchVo.setUserList(userVOPage.getRecords());
                    break;
                case IMAGE:
                    ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
                    imageQueryRequest.setSearchText(searchText);
                    Page<Image> imagePage = imageService.getImageByPage(imageQueryRequest);
                    searchVo.setImageList(imagePage.getRecords());
                    break;
                default:
                    break;
            }
        }
        return searchVo;
    }
}
```



#### 适配器模式

接入的数据源或者接口必须遵循这个接口规范

```java
@Service
public class ImageDataSource implements SearchDataSource<Image> {

    @Resource
    private ImageService imageService;

    @Override
    public Page<Image> doSearch(String searchText, int current, int pageSize) {
        ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
        imageQueryRequest.setSearchText(searchText);
        imageQueryRequest.setCurrent(current);
        imageQueryRequest.setPageSize(pageSize);
        Page<Image> imageByPage = imageService.getImageByPage(imageQueryRequest);
        return imageByPage;
    }
}
```

需要适配或接入不同的数据源，可能提供的方法参数和平台调用的方法参数不一致，可以使用适配器模式



![image-20241129230331103](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241129230331103.png)

适配器模式通过封装对象将复杂的转换过程隐藏于幕后。 被封装的对象甚至察觉不到适配器的存在。 例如， 你可以使用一个将所有数据转换为英制单位 （如英尺和英里） 的适配器封装运行于米和千米单位制中的对象。



下面这段代码

postService，userService，imageService分别调用的方法逻辑相同参数不同，可使用接口统一调用

```java
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @Resource
    private ImageService imageService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return searchVo;
        }
        String searchText = searchQueryRequest.getSearchText();
        String searchType = searchQueryRequest.getSearchType();
        if (StringUtils.isBlank(searchType)) {
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);
            switch (searchTypeEnum) {
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postService.listPostVoPage(postQueryRequest, httpServletRequest);
                    searchVo.setPostList(postVOPage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.listUserVoPage(userQueryRequest);
                    searchVo.setUserList(userVOPage.getRecords());
                    break;
                case IMAGE:
                    ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
                    imageQueryRequest.setSearchText(searchText);
                    Page<Image> imagePage = imageService.getImageByPage(imageQueryRequest);
                    searchVo.setImageList(imagePage.getRecords());
                    break;
                default:
                    break;
            }
        }
        return searchVo;
    }
}
```

下面实现一个简单的适配器



```java
新建一个数据源接口
/**
 * @author tuaofei
 * @description 查询数据源
 * @date 2024/11/29
 */
public interface SearchDataSource<T> {

    /**
     * 通用查询接口
     * @param searchText
     * @param current
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, int current, int pageSize);
}
```

postService.listPostVoPage(postQueryRequest, httpServletRequest)

新增PostDataSource数据源

```java
@Service
@Slf4j
public class PostDataSource implements SearchDataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, int current, int pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        //HttpServletRequest 这里没法获取,考虑改造接口或改造方法，根据具体情况判断
        Page<PostVO> postVOPage = postService.listPostVoPage(postQueryRequest, null);
        return postVOPage;
    }
}
```



新增UserDataSource 数据源

userService.listUserVoPage(userQueryRequest)

```java
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
```



新增ImageDataSource 数据源

imageService.getImageByPage(imageQueryRequest)

```java
@Service
public class ImageDataSource implements SearchDataSource<Image> {

    @Resource
    private ImageService imageService;

    @Override
    public Page<Image> doSearch(String searchText, int current, int pageSize) {
        ImageQueryRequest imageQueryRequest = new ImageQueryRequest();
        imageQueryRequest.setSearchText(searchText);
        imageQueryRequest.setCurrent(current);
        imageQueryRequest.setPageSize(pageSize);
        Page<Image> imageByPage = imageService.getImageByPage(imageQueryRequest);
        return imageByPage;
    }
}
```

修改SearchFacade里面调用service的逻辑,这样就可以统一调用相同参数的方法，转换逻辑交给具体的数据源

```java
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private ImageDataSource imageDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return searchVo;
        }
        String searchText = searchQueryRequest.getSearchText();
        String searchType = searchQueryRequest.getSearchType();
        int current = searchQueryRequest.getCurrent();
        int pageSize = searchQueryRequest.getPageSize();
        if (StringUtils.isBlank(searchType)) {
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<Image>> imageTask = CompletableFuture.supplyAsync(() -> {
                Page<Image> imagePage = imageDataSource.doSearch(searchText, current, pageSize);
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);
            switch (searchTypeEnum) {
                case POST:
                    Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                    ;
                    searchVo.setPostList(postVOPage.getRecords());
                    break;
                case USER:
                    Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                    searchVo.setUserList(userVOPage.getRecords());
                    break;
                case IMAGE:
                    Page<Image> imagePage = imageDataSource.doSearch(searchText, current, pageSize);
                    searchVo.setImageList(imagePage.getRecords());
                    break;
                default:
                    break;
            }
        }
        return searchVo;
    }
}
```

#### 注册器模式

提前把需要用到的bean实例化

```java
package com.xiaofei.site.search.datasource;

import com.xiaofei.site.search.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/29
 */
@Component
public class DataSourceRegistry {

    @Resource
    private ImageDataSource imageDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    private Map<String, SearchDataSource<T>> dataSourceMap;

    /**
     * 在依赖注入完成后，执行
     */
    @PostConstruct
    public void doInit() {
        dataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.IMAGE.getValue(), imageDataSource);
        }};
    }

    public SearchDataSource getDataSourceByType(String searchType) {
        if (dataSourceMap == null) {
            return null;
        }
        return dataSourceMap.get(searchType);
    }


}
```

使用

```java
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return searchVo;
        }
        String searchText = searchQueryRequest.getSearchText();
        String searchType = searchQueryRequest.getSearchType();
        int current = searchQueryRequest.getCurrent();
        int pageSize = searchQueryRequest.getPageSize();
        if (StringUtils.isBlank(searchType)) {
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource postDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.POST.getValue());
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource userDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.USER.getValue());
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<Image>> imageTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource imageDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.IMAGE.getValue());
                Page<Image> imagePage = imageDataSource.doSearch(searchText, current, pageSize);
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
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            SearchDataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(searchType);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            List<?> records = page.getRecords();
            searchVo.setDataList(records);
        }
        return searchVo;
    }
}
```



## 11.搜索优化 ES

![image-20241130224418217](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130224418217.png)

搜索不够灵活

如果想搜索到下面这个记录就搜不到，因为现在是使用数据库的like查询



https://www.elastic.co/cn/downloads/elasticsearch

分布式搜索和分析引擎，用于实时地存储、搜索和分析海量数据



### 下载

这里使用7.17版本

### elasticsearch

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/zip-windows.html

### logstash

Logstash 是一个开源的数据采集引擎，具有实时管道传输功能。Logstash 能够将来自单独数据源的数据动态集中到一起，对这些数据加以标准化并传输到您所选的地方。

https://www.elastic.co/guide/en/logstash/7.17/setup-logstash.html

### kibana

搜索、监控、保护、分析、管理数据

https://www.elastic.co/guide/en/kibana/7.17/install.html

### 启动

**elasticsearch**

运行bin/elasticsearch.bat

![image-20241201224752471](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201224752471.png)



**kibana**

运行bin/kibana.bat

访问：http://localhost:5601

![image-20241201224813766](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201224813766.png)



**logstash**

运行bin/logstash.bat

![image-20241201224935076](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201224935076.png)

如果出现上面这种闪退或报错，在bin目录下命令行执行

```cmd
logstash -f ../config/logstash-sample.conf
```

`conf`目录下的配置文件`logstash-sample.conf`

```
# Sample Logstash configuration for creating a simple
# Beats -> Logstash -> Elasticsearch pipeline.

input {
  beats {
    port => 5044
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
    #user => "elastic"
    #password => "changeme"
  }
}
```

![image-20241201224834216](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201224834216.png)



### 使用



#### 首次进入

![image-20241201225424395](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201225424395.png)

#### 开发者工具

![image-20241201225914240](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201225914240.png)

![image-20241201225928112](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201225928112.png)



### 启动端口

9200：提供api接口
9300：集群内部通信使用

### 索引

正向索引:类似目录

倒排索引:根据内容找文章

先把要存储的内容切词,构建索引;通过需要搜索的内容,切词根据索引去查询对应的文章

```
文章1:我是鲨鱼
文章2:我是小猫

切词
我是 鲨鱼
我是 小猫
```



| 词   | 内容id      |
| ---- | ----------- |
| 我是 | 文章1,文章2 |
| 鲨鱼 | 文章1       |
| 小猫 | 文章2       |

搜索"我是小猫"

根据[倒排索引表]找到对应的文章1,2



### 调用方式

#### resuful api

get请求 http://localhost:9200

```json
{
    "name": "PC-20230622ZAWQ",
    "cluster_name": "elasticsearch",
    "cluster_uuid": "NVeztQsWQDGTN32D8LkS3Q",
    "version": {
        "number": "7.17.12",
        "build_flavor": "default",
        "build_type": "zip",
        "build_hash": "e3b0c3d3c5c130e1dc6d567d6baef1c73eeb2059",
        "build_date": "2023-07-20T05:33:33.690180787Z",
        "build_snapshot": false,
        "lucene_version": "8.11.1",
        "minimum_wire_compatibility_version": "6.8.0",
        "minimum_index_compatibility_version": "6.0.0-beta1"
    },
    "tagline": "You Know, for Search"
}
```



#### kibana devtools

开发工具查询

<img src="https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130232225854.png" alt="image-20241130232225854" style="zoom:80%;" />

![image-20241130232341488](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130232341488.png)

##### 客户端调用

java客户端

https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.17/_javadoc.html

##### JAVA操作ES

javaApi

https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.17/transport-client.html



Spring Data Elasticsearch

https://docs.spring.io/spring-data/elasticsearch/docs/

### ES语法

#### DSL(推荐使用)

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/query-dsl.html

增加

```json
 PUT user/_doc/1
 {
  "name":"土澳菲",
  "age":18
 }
```

![image-20241130232818603](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130232818603.png)

删除

```json
DELETE user
```

![image-20241130233452377](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130233452377.png)

修改

```json
PUT user/_doc/1
 {
  "name":"土澳菲1",
  "age":19
 }
```

查询

```json
GET user/_doc/1
```

![image-20241130232942891](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130232942891.png)

```json
GET user/_search
{
  "query": {
    "match_all": { }
  },
  "fields": [
    "name"
  ],
  "sort": [
    {
      "age": "desc"
    }
  ]
}
```

![image-20241130233005635](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130233005635.png)

#### ESL

EQL queries require an event category and a matching condition

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/eql.html



```json
POST logs-my_app-default/_doc
{
  "@timestamp": "2099-05-06T16:21:15.000Z",
  "event": {
    "original": "192.0.2.42 - - [06/May/2099:16:21:15 +0000] \"GET /images/bg.jpg HTTP/1.0\" 200 24736"
  }
}

GET logs-my_app-default/_eql/search
{
  "query": """
  any where 1==1
  """
}
```



#### SQL

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/xpack-sql.html

```json
PUT /library/book/_bulk?refresh&pretty
{"index":{"_id": "Leviathan Wakes"}}
{"name": "Leviathan Wakes", "author": "James S.A. Corey", "release_date": "2011-06-02", "page_count": 561}
{"index":{"_id": "Hyperion"}}
{"name": "Hyperion", "author": "Dan Simmons", "release_date": "1989-05-26", "page_count": 482}
{"index":{"_id": "Dune"}}
{"name": "Dune", "author": "Frank Herbert", "release_date": "1965-06-01", "page_count": 604}

POST /_sql?format=txt&pretty
{

  "query": "SELECT * FROM library WHERE release_date < \u00272000-01-01\u0027"

}
```

![image-20230810222027260](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230810222027260.png)

![image-20241130235725836](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130235725836.png)

#### Scripting

*Painless* is a performant, secure scripting language designed specifically for Elasticsearch

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/modules-scripting.html



### mappering

理解为数据库表结构

支持动态mapping,表结构可以改变

```json
PUT /my-index-000001
{
  "mappings": {
    "properties": {
      "age":    { "type": "integer" },  
      "email":  { "type": "keyword"  }, 
      "name":   { "type": "text"  }     
    }
  }
}
```

![image-20241130234806356](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241130234806356.png)



### 分词器

#### 内置分词器

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/analysis-tokenizers.html

#### **标准分词器**

```json
POST /_analyze?pretty
{
  "tokenizer": "standard",
  "text": "I love wht"
}
结果
{
  "tokens" : [
    {
      "token" : "I",
      "start_offset" : 0,
      "end_offset" : 1,
      "type" : "<ALPHANUM>",
      "position" : 0
    },
    {
      "token" : "love",
      "start_offset" : 2,
      "end_offset" : 6,
      "type" : "<ALPHANUM>",
      "position" : 1
    },
    {
      "token" : "wht",
      "start_offset" : 7,
      "end_offset" : 10,
      "type" : "<ALPHANUM>",
      "position" : 2
    }
  ]
}
```

**空格分词器**

```json
POST _analyze
{
  "tokenizer": "whitespace",
  "text": "The 2 QUICK Brown-Foxes jumped over the lazy dog's bone."
}

[ The, 2, QUICK, Brown-Foxes, jumped, over, the, lazy, dog's, bone. ]
```

**关键词分词器**

相当于不分词

```json
POST _analyze
{
  "tokenizer": "keyword",
  "text": "New York"
}
```

#### IK分词器

对中文友好

https://github.com/medcl/elasticsearch-analysis-ik

<img src="https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201215951118.png" alt="image-20241201215951118" style="zoom: 80%;" />



##### 安装

###### 方式1

远程安装：bin/elasticsearch-plugin install https://get.infini.cloud/elasticsearch/analysis-ik/7.17.12



###### 方式2

下载解压包，本地安装：

在es安装目录新建`plugins/IK`目录，把插件包解压到该目录下，使用命令行重启es，防止报错闪退

如果没有对应版本下载相近的版本，解压后修改plugin-descriptor.properties文件里面的elasticsearch.version保持版本号一致就可以

![image-20241201220554818](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241201220554818.png)





**ik_smart**

智能分词，尽可能取合适的词

```json
POST /_analyze?pretty
{
  "tokenizer": "ik_smart",
  "text": "我是小猪猪"
}
```

**ik_max_word**

最细粒度的分词

```json
POST /_analyze?pretty
{
  "tokenizer": "ik_max_word",
  "text": "我不是小猪猪,我是大猪猪."
}
```

### 打分机制

https://www.elastic.co/guide/en/elasticsearch/guide/master/controlling-relevance.html



有3条内容：

1. 小猪猪
2. 男朋友是大猪猪
3. 我是臭猪猪

用户搜索：

1.猪猪：会匹配到第1条，匹配关键词，更短



## 12.使用ES实现搜索接口



es中尽量存放需要搜索的字段

```json
aliases：别名（为了后续方便数据迁移）
字段类型是 text，这个字段是可被分词的、可模糊查询的；而如果是 keyword，只能完全匹配、精确查询。
analyzer（存储时生效的分词器）：用 ik_max_word，拆的更碎、索引更多，更有可能被搜出来
search_analyzer（查询时生效的分词器）：用 ik_smart，更偏向于用户想搜的分词
如果想要让 text 类型的分词字段也支持精确查询，可以创建 keyword 类型的子字段：
```

```json
#创建文章索引{fields：子字段也支持精确查询}
PUT /post_v1

##创建mapping
PUT /post_v1/_mapping
{
  "properties":{
      "title":{
        "type":"text",
        "analyzer":"ik_max_word",
        "search_analyzer":"ik_smart",
        "fields":{
          "keyword":{
            "type":"keyword",
            "ignore_above":256
          }
        }
      },
      "content":{
        "type":"text",
        "analyzer":"ik_max_word",
        "search_analyzer":"ik_smart",
        "fields":{
          "keyword":{
            "type":"keyword",
            "ignore_above":256
          }
        }
      },
      "tags" : {
        "type": "text",
        "fields": {
          "keyword": {
           "type": "keyword"
          }
      }
      },
      "userId":{
        "type":"long"
      },
      "createTime":{
        "type":"date"
      },
      "updateTime":{
        "type":"date"
      },
      "isDelete":{
        "type":"boolean"
      }
    }
}

##创建别名
POST _aliases
{
  "actions": [
    {
      "add": {
        "index": "post_v1",
        "alias": "post"
      }
    }
  ]
}

#检查现有索引和别名
GET _cat/indices
GET _cat/aliases
#删除现有的索引
DELETE /post_v1


#isDelete应该为integer类型，需要重建索引
PUT /post_v2
PUT /post_v2/_mapping
{
  "properties":{
      "title":{
        "type":"text",
        "analyzer":"ik_max_word",
        "search_analyzer":"ik_smart",
        "fields":{
          "keyword":{
            "type":"keyword",
            "ignore_above":256
          }
        }
      },
      "content":{
        "type":"text",
        "analyzer":"ik_max_word",
        "search_analyzer":"ik_smart",
        "fields":{
          "keyword":{
            "type":"keyword",
            "ignore_above":256
          }
        }
      },
      "tags" : {
        "type": "text",
        "fields": {
          "keyword": {
           "type": "keyword"
          }
      }
      },
      "userId":{
        "type":"long"
      },
      "createTime":{
        "type":"date"
      },
      "updateTime":{
        "type":"date"
      },
      "isDelete":{
        "type":"integer"
      }
    }
}
#复制数据
POST _reindex
{
  "source": {
    "index": "post_v1"
  },
  "dest": {
    "index": "post_v2"
  }
}
```



版本选择

https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/versions.html

![image-20241202204913715](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241202204913715.png)





**CRUD**



es查询出来对应的实体

```java
package com.xiaofei.site.search.model.dto.post;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.xiaofei.site.search.model.entity.Post;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子 ES 包装类
 **/
// todo 取消注释开启 ES（须先配置 ES）
@Document(indexName = "post")
@Data
public class PostEsDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    /**
     * 对象转包装类
     *
     * @param post
     * @return
     */
    public static PostEsDTO objToDto(Post post) {
        if (post == null) {
            return null;
        }
        PostEsDTO postEsDTO = new PostEsDTO();
        BeanUtils.copyProperties(post, postEsDTO);
        String tagsStr = post.getTags();
        if (StringUtils.isNotBlank(tagsStr)) {
            postEsDTO.setTags(JSONUtil.toList(tagsStr, String.class));
        }
        return postEsDTO;
    }

    /**
     * 包装类转对象
     *
     * @param postEsDTO
     * @return
     */
    public static Post dtoToObj(PostEsDTO postEsDTO) {
        if (postEsDTO == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postEsDTO, post);
        List<String> tagList = postEsDTO.getTags();
        if (CollUtil.isNotEmpty(tagList)) {
            post.setTags(JSONUtil.toJsonStr(tagList));
        }
        return post;
    }
}
```

### es配置

没有用户密码可填可不填

```properties
elasticsearch:
   uris: http://localhost:9200
   username: root
   password: 123456
```





(1)继承ElasticsearchRepository，提供简单的crud

```java
public interface CrudRepository<T, ID> extends Repository<T, ID> {
    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();
}
```

ES 中，_开头的字段表示系统默认字段，比如 _id，如果系统不指定，会自动生成。但是不会在 _source 字段中补充 id 的值，所以建议手动指定。



支持根据方法名自动生成方法

```java
List<PostEsDTO> findByUserId(Long userId);

List<PostEsDTO> findByTitle(String title);
```



(2)ElasticsearchRestTemplate,Spring 默认给我们提供的操作 es 的客户端对象，也提供了增删改查，它的增删改查更灵活，适用于更复杂的操作，返回结果更完整，但需要自己解析。



取参数->把参数组合为 ES 支持的搜索条件->从返回值中取结果

```java
public Page<Post> searchFromEs(PostQueryRequest postQueryRequest) {
        Long id = postQueryRequest.getId();
        Long notId = postQueryRequest.getNotId();
        String searchText = postQueryRequest.getSearchText();
        String title = postQueryRequest.getTitle();
        String content = postQueryRequest.getContent();
        List<String> tagList = postQueryRequest.getTags();
        List<String> orTagList = postQueryRequest.getOrTags();
        Long userId = postQueryRequest.getUserId();
        // es 起始页为 0
        long current = postQueryRequest.getCurrent() - 1;
        long pageSize = postQueryRequest.getPageSize();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));
        if (id != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        }
        if (notId != null) {
            boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", notId));
        }
        if (userId != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("userId", userId));
        }
        // 必须包含所有标签
        if (CollUtil.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("tags", tag));
            }
        }
        // 包含任何一个标签即可
        if (CollUtil.isNotEmpty(orTagList)) {
            BoolQueryBuilder orTagBoolQueryBuilder = QueryBuilders.boolQuery();
            for (String tag : orTagList) {
                orTagBoolQueryBuilder.should(QueryBuilders.termQuery("tags", tag));
            }
            orTagBoolQueryBuilder.minimumShouldMatch(1);
            boolQueryBuilder.filter(orTagBoolQueryBuilder);
        }
        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("description", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按标题检索
        if (StringUtils.isNotBlank(title)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", title));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按内容检索
        if (StringUtils.isNotBlank(content)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", content));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withSorts(sortBuilder).build();
        SearchHits<PostEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, PostEsDTO.class);
        Page<Post> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        List<Post> resourceList = new ArrayList<>();
        // 查出结果后，从 db 获取最新动态数据（比如点赞数）
        if (searchHits.hasSearchHits()) {
            List<SearchHit<PostEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> postIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());
            List<Post> postList = baseMapper.selectBatchIds(postIdList);
            if (postList != null) {
                Map<Long, List<Post>> idPostMap = postList.stream().collect(Collectors.groupingBy(Post::getId));
                postIdList.forEach(postId -> {
                    if (idPostMap.containsKey(postId)) {
                        resourceList.add(idPostMap.get(postId).get(0));
                    } else {
                        // 从 es 清空 db 已物理删除的数据
                        String delete = elasticsearchRestTemplate.delete(String.valueOf(postId), PostEsDTO.class);
                        log.info("delete post {}", delete);
                    }
                });
            }
        }
        page.setRecords(resourceList);
        return page;
    }
```

### 查询DSL

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/query-filter-context.html

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/query-dsl-bool-query.html

![image-20230814224241744](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230814224241744.png)

![image-20241203201108759](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203201108759.png)

wildcard 模糊查询

regexp 正则匹配查询

查询结果中，score 代表匹配分数

![image-20230814224446191](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230814224446191.png)

动静分离设计：先模糊筛选静态数据，查出数据后，再根据查到的内容 id 去数据库查找到 



高亮

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/highlighting.html

```json
GET /_search
{
  "query": {
    "match": { "content": "kimchy" }
  },
  "highlight": {
    "fields": {
      "content": {}
    }
  }
}
```

搜索建议

https://www.elastic.co/guide/en/elasticsearch/reference/7.17/search-suggesters.html

```json
POST my-index-000001/_search
{
  "query" : {
    "match": {
      "message": "tring out Elasticsearch"
    }
  },
  "suggest" : {
    "my-suggestion" : {
      "text" : "tring out Elasticsearch",
      "term" : {
        "field" : "message" //提示字段
      }
    }
  }
}
```



### 改造现有查询方法

```java
public interface PostService extends IService<Post> {
/**
     * 从 ES 查询
     *
     * @param postQueryRequest
     * @return
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);
}

@Service
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

@Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

 @Override
    public Page<Post> searchFromEs(PostQueryRequest postQueryRequest) {
        Long id = postQueryRequest.getId();
        Long notId = postQueryRequest.getNotId();
        String searchText = postQueryRequest.getSearchText();
        String title = postQueryRequest.getTitle();
        String content = postQueryRequest.getContent();
        List<String> tagList = postQueryRequest.getTags();
        List<String> orTagList = postQueryRequest.getOrTags();
        Long userId = postQueryRequest.getUserId();
        // es 起始页为 0
        long current = postQueryRequest.getCurrent() - 1;
        long pageSize = postQueryRequest.getPageSize();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));
        if (id != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        }
        if (notId != null) {
            boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", notId));
        }
        if (userId != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("userId", userId));
        }
        // 必须包含所有标签
        if (CollUtil.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("tags", tag));
            }
        }
        // 包含任何一个标签即可
        if (CollUtil.isNotEmpty(orTagList)) {
            BoolQueryBuilder orTagBoolQueryBuilder = QueryBuilders.boolQuery();
            for (String tag : orTagList) {
                orTagBoolQueryBuilder.should(QueryBuilders.termQuery("tags", tag));
            }
            orTagBoolQueryBuilder.minimumShouldMatch(1);
            boolQueryBuilder.filter(orTagBoolQueryBuilder);
        }
        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("description", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按标题检索
        if (StringUtils.isNotBlank(title)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", title));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按内容检索
        if (StringUtils.isNotBlank(content)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", content));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withSorts(sortBuilder).build();
        SearchHits<PostEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, PostEsDTO.class);
        Page<Post> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        List<Post> resourceList = new ArrayList<>();
        // 查出结果后，从 db 获取最新动态数据（比如点赞数）
        if (searchHits.hasSearchHits()) {
            List<SearchHit<PostEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> postIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());
            List<Post> postList = baseMapper.selectBatchIds(postIdList);
            if (postList != null) {
                Map<Long, List<Post>> idPostMap = postList.stream().collect(Collectors.groupingBy(Post::getId));
                postIdList.forEach(postId -> {
                    if (idPostMap.containsKey(postId)) {
                        resourceList.add(idPostMap.get(postId).get(0));
                    } else {
                        // 从 es 清空 db 已物理删除的数据
                        String delete = elasticsearchRestTemplate.delete(String.valueOf(postId), PostEsDTO.class);
                        log.info("delete post {}", delete);
                    }
                });
            }
        }
        page.setRecords(resourceList);
        return page;
    }
}

@Service
@Slf4j
public class PostDataSource implements SearchDataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, int current, int pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        HttpServletRequest httpServletRequest = null;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            httpServletRequest = servletRequestAttributes.getRequest();
        }
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        Page<PostVO> postVOPage = postService.getPostVOPage(postPage, httpServletRequest);
        return postVOPage;
    }
}
```



### 上传文件怎么实现文件内容搜索

思路：上传文件，获取文件内容base64，使用es的ingest-attachment文本抽取管道转换为文字存储



#### 创建文件实体

```java
package com.xiaofei.site.search.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Data
@TableName("file")
public class FilePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 存储路径（相对路径）
     */
    private String filePath;

    /**
     * 文件类型/MIME类型，如 image/jpeg
     */
    private String fileType;

    /**
     * 文件扩展名，如 .jpg
     */
    private String fileExtension;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件MD5值，用于文件去重
     */
    private String md5;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 业务
     */
    private String biz;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 文件内容
     */
    @TableField(value = "content", jdbcType = JdbcType.LONGVARCHAR)
    private String content;
}
```

#### 建表语句

```sql
CREATE TABLE `file` (
                        `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
                        `fileName` VARCHAR(255) NOT NULL COMMENT '原始文件名',
                        `filePath` VARCHAR(255) NOT NULL COMMENT '存储路径（相对路径）',
                        `fileType` VARCHAR(100) NOT NULL COMMENT '文件类型/MIME类型',
                        `fileExtension` VARCHAR(10) COMMENT '文件扩展名',
                        `fileSize` BIGINT NOT NULL COMMENT '文件大小（字节）',
                        `md5` VARCHAR(32) NOT NULL COMMENT '文件MD5值',
                        `userId` BIGINT COMMENT '创建用户 id',
                        `biz` VARCHAR(50) NOT NULL COMMENT '业务',
                        `isDelete` TINYINT DEFAULT 0 COMMENT '是否删除',
                        `downloadCount` INT DEFAULT 0 COMMENT '下载次数',
                        `description` TEXT COMMENT '文件描述',
                        `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `content` LONGTEXT COMMENT '文件内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';
```



#### 开发后台上传文件逻辑

##### 上传配置

```java
file:
  upload:
    path: D:/upload  # 文件上传根路径
    max-size: 104857600              # 最大文件大小（字节）
    allowed-types: # 允许的文件类型
      - image/jpeg
      - image/png
      - application/pdf
```

通过@Component和@ConfigurationProperties加载配置

```java
package com.xiaofei.site.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Data
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileConfig {

    /**
     * 上传根路径
     */
    private String path;

    /**
     * 最大文件大小（字节）
     */
    private Long maxSize;

    /**
     * 允许的文件类型列表
     */
    private List<String> allowedTypes;
}
```

##### filemapper

```java
public interface FileMapper extends BaseMapper<FilePo> {
}
```

##### fileservice及实现类

```java
public enum FileUploadBizEnum {

    USER_AVATAR("用户头像", "user_avatar"),
    DOC("文档", "doc");
    ...
}
```

```java
public interface FileService extends IService<FilePo> {
    
    FilePo uploadFile(MultipartFile file, FileUploadBizEnum fileUploadBizEnum);
    
}
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FilePo> implements FileService {
    @Resource
    private FileMapper fileMapper;
    
    @Resource
    private FileConfig fileConfig;
    
@Override
    public FilePo uploadFile(MultipartFile file, FileUploadBizEnum fileUploadBizEnum) {
        try {

            //扩展名
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            // 1. 生成存储路径
            String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String fileName = generateFileName(file.getOriginalFilename());
            String relativePath = String.format("%s/%s/%s", datePath, fileUploadBizEnum.getValue(), fileName);
            String absolutePath = fileConfig.getPath() + "/" + relativePath;

            // 2. 确保目录存在
            File directory = new File(absolutePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 3. 保存文件
            File dest = new File(absolutePath);
            file.transferTo(dest);

            // 4. 计算MD5
            String md5 = calculateMD5(dest);

            // 5. 保存文件信息到数据库
            Date nowDate = new Date();

            FilePo filePo = new FilePo();
            filePo.setFileName(file.getOriginalFilename());
            filePo.setFilePath(absolutePath);
            filePo.setFileType(file.getContentType());
            filePo.setFileSize(file.getSize());
            filePo.setFileExtension(extension);
            filePo.setMd5(md5);
            filePo.setBiz(fileUploadBizEnum.getValue());
            filePo.setCreateTime(nowDate);
            filePo.setUpdateTime(nowDate);
            filePo.setIsDelete(0);

            int insert = fileMapper.insert(filePo);
            if (insert > 0) {
                return filePo;
            }
            return null;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "文件上传失败");
        }
    }
}
```

##### filecontroller控制层

接收上传文件参数

```java
package com.xiaofei.site.search.model.dto.file;
import lombok.Data;
import java.io.Serializable;

/**
 * 文件上传请求
 */
@Data
public class UploadFileRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}
```



```java
/**
 * 文件接口
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
@Resource
    private FileService fileService;

@PostMapping("/uploadEs")
    public BaseResponse<FileVo> uploadFileByEs(@RequestPart("file") MultipartFile multipartFile,
                                             @RequestPart UploadFileRequest uploadFileRequest, HttpServletRequest request) {
        String biz = uploadFileRequest.getBiz();
        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
        if (fileUploadBizEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, fileUploadBizEnum);
        FilePo filePo = fileService.uploadFile(multipartFile, fileUploadBizEnum);
        return ResultUtils.success(FilePoToVoUtils.poToVo(filePo));
    }
    
    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 10 * 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 10M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }
}
```



#### 开发前台上传文件逻辑

##### 文件上传接口

扩展reqaxios.ts，支持文件上传

```ts
export default function (method: string, url: string, data: any, config = {}) {
  method = method.toLowerCase();
  if (method == "post") {
    return reqAxios.post(url, data);
  } else if (method == "get") {
    return reqAxios.get(url, { params: data });
  } else if (method == "file") {
    return reqAxios.post(url, data, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      ...config, // 合并自定义配置
    });
  } else if (method == "download") {
    return reqAxios.post(url, data, {
      responseType: "blob",
      headers: {
        "Content-Type": "multipart/form-data",
      },
      ...config,
    });
  } else {
    console.error("未知的method" + method);
    return false;
  }
}
```

seatch.ts增加上传文件接口

```ts
/**
 * 上传文件到ES
 * @param formData
 * @param config
 */
export const fileUploadEs = (formData: FormData, config = {}) =>
  ReqAxios("file", "/file/uploadEs", formData, config);
```



##### FilePage.vue文件上传页面

```vue
<template>
  <div>
    <a-upload-dragger
      v-model:fileList="fileList"
      name="file"
      :multiple="true"
      :customRequest="customRequest"
      :before-upload="beforeUpload"
      @change="handleChange"
      @drop="handleDrop"
    >
      <p class="ant-upload-drag-icon">
        <inbox-outlined></inbox-outlined>
      </p>
      <p class="ant-upload-text">点击 或 拖拽 文件 到这个区域上传</p>
      <p class="ant-upload-hint">
        支持单次或批量上传，禁止上传公司或其它乐队文件
      </p>
    </a-upload-dragger>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import type { UploadChangeParam } from "ant-design-vue";
import { message } from "ant-design-vue";
import { fileUpload, fileUploadEs } from "@/request/Search";
import MyDivider from "@/components/MyDivider.vue";

const customRequest = (options: any) => {
  const { onSuccess, onError, file, onProgress } = options;
  const formData = new FormData();
  formData.append("file", file);
  // 使用对象的形式
  const uploadFileRequest = {
    biz: "doc",
  };
  formData.append(
    "uploadFileRequest",
    new Blob([JSON.stringify(uploadFileRequest)], {
      type: "application/json",
    })
  );
  fileUploadEs(formData, {
    onUploadProgress: (progressEvent) => {
      const percent = Math.round(
        (progressEvent.loaded * 100) / progressEvent.total
      );
      onProgress({ percent });
    },
  })
    .then((data: any) => {
      onSuccess(data, file);
    })
    .catch((error: any) => {
      onError(error, file);
    });
};

const beforeUpload = (file: any) => {
  const isLt10M = file.size / 1024 / 1024 < 100;
  if (!isLt10M) {
    message.error("Image must smaller than 100MB!");
  }
  return isLt10M;
};

const fileList = ref([]);
const handleChange = (info: UploadChangeParam) => {
  const status = info.file.status;
  if (status !== "uploading") {
    console.log(info.file, info.fileList);
  }
  if (status === "done") {
    message.success(`${info.file.name} 文件上传成功.`);
  } else if (status === "error") {
    message.error(`${info.file.name} 文件上传失败.`);
  }
};

const handleDrop = (e: DragEvent) => {
  console.log(e);
};
</script>
```

> ```
> @RequestPart("file") MultipartFile multipartFile,
> @RequestPart UploadFileRequest uploadFileRequest, 
> HttpServletRequest request
> 
> 后台参数接收
> 
> 前台使用post multipart/form-data传参
> const formData = new FormData();
>   formData.append("file", file);
>   // 使用对象的形式
>   const uploadFileRequest = {
>     biz: "doc",
>   };
>   formData.append(
>     "uploadFileRequest",
>     new Blob([JSON.stringify(uploadFileRequest)], {
>       type: "application/json",
>     })
>   );
> ```

上传成功

![image-20241210212656168](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241210212656168.png)

![image-20241210212720756](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241210212720756.png)





#### 扩展搜索接口分类

##### 后端扩展

新增和前端交互的fileVo

```java
package com.xiaofei.site.search.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Data
public class FileVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小（格式化后的），如：1.5MB
     */
    private String fileSizeFormat;

    private String fileExtension;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 上传用户名
     */
    private Long userId;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 文件预览URL
     */
    private String previewUrl;

    /**
     * 文件下载URL
     */
    private String downloadUrl;

    /**
     * 是否可预览
     */
    private Boolean canPreview;

    /**
     * 是否可下载
     */
    private Boolean canDownload;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 业务
     */
    private String biz;

    private String content;  // 新增字段，用于存储base64内容
}
```

新增文件搜索接口

文件搜索请求实体

```java
package com.xiaofei.site.search.model.dto.file;

import com.xiaofei.site.search.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 */
@Data
public class FileQueryRequest extends PageRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private String fileName;

    private String content;

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
```

fileserve提供搜索服务

```java
public interface FileService extends IService<FilePo> {

    Page<FileVo> listFileVoPage(FileQueryRequest fileQueryRequest);

    FilePo uploadFile(MultipartFile file, FileUploadBizEnum fileUploadBizEnum);

}
@Override
    public Page<FileVo> listFileVoPage(FileQueryRequest fileQueryRequest) {
        int current = fileQueryRequest.getCurrent();
        int pageSize = fileQueryRequest.getPageSize();
        if (fileQueryRequest == null) {
            return new Page<>(current, pageSize);
        }
        String fileName = fileQueryRequest.getFileName();
        String biz = fileQueryRequest.getBiz();
        QueryWrapper<FilePo> queryWrapper = new QueryWrapper<>();


        queryWrapper.lambda()
                // 文件名模糊查询
                .like(StringUtils.isNotBlank(fileName), FilePo::getFileName, fileName)
                // 业务类型精确匹配
                .eq(StringUtils.isNotBlank(biz), FilePo::getBiz, biz)
                // 按上传时间倒序
                .orderByDesc(FilePo::getCreateTime);

        Page<FilePo> page = this.page(new Page<>(current, pageSize), queryWrapper);
        List<FilePo> filePos = page.getRecords();
        List<FileVo> fileVos = new ArrayList<>();
        if (CollUtil.isNotEmpty(filePos)) {
            for (FilePo filePo : filePos) {
                fileVos.add(FilePoToVoUtils.poToVo(filePo));
            }
        }
        Page<FileVo> fileVoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        fileVoPage.setRecords(fileVos);
        return fileVoPage;
    }
```



新增file数据源

```java
@Service
public class FileDataSource implements SearchDataSource<FileVo>{

    @Resource
    private FileService fileService;

    @Override
    public Page<FileVo> doSearch(String searchText, int current, int pageSize) {

        FileQueryRequest fileQueryRequest = new FileQueryRequest();
        fileQueryRequest.setFileName(searchText);
        fileQueryRequest.setCurrent(current);
        fileQueryRequest.setPageSize(pageSize);
        Page<FileVo> fileVoPage = fileService.listFileVoPage(fileQueryRequest);
        return fileVoPage;
    }
}
```

扩展数据源注册DataSourceRegistry

```java
package com.xiaofei.site.search.datasource;

import com.xiaofei.site.search.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/11/29
 */
@Component
public class DataSourceRegistry {

    @Resource
    private ImageDataSource imageDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private FileDataSource fileDataSource;

    private Map<String, SearchDataSource<T>> dataSourceMap;

    /**
     * 在依赖注入完成后，执行
     */
    @PostConstruct
    public void doInit() {
        dataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.IMAGE.getValue(), imageDataSource);
            put(SearchTypeEnum.FILE.getValue(), fileDataSource);
        }};
    }

    public SearchDataSource getDataSourceByType(String searchType) {
        if (dataSourceMap == null) {
            return null;
        }
        return dataSourceMap.get(searchType);
    }
}
```

修改门面模式，增加文件数据搜索

```java
package com.xiaofei.site.search.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tuaofei
 * @description 查询类型
 * @date 2024/11/28
 */
public enum SearchTypeEnum {

    POST("帖子", "post"),
    USER("用户", "user"),
    IMAGE("图片", "image"),
    FILE("文件", "file");

    ...
}

```



```java
package com.xiaofei.site.search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaofei.site.search.datasource.*;
import com.xiaofei.site.search.model.dto.search.SearchQueryRequest;
import com.xiaofei.site.search.model.entity.Image;
import com.xiaofei.site.search.model.enums.SearchTypeEnum;
import com.xiaofei.site.search.model.vo.FileVo;
import com.xiaofei.site.search.model.vo.PostVO;
import com.xiaofei.site.search.model.vo.SearchVo;
import com.xiaofei.site.search.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author tuaofei
 * @description 查询-门面模式
 * @date 2024/11/29
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public SearchVo searchAll(@RequestBody SearchQueryRequest searchQueryRequest, HttpServletRequest httpServletRequest) {
        SearchVo searchVo = new SearchVo();
        if (searchQueryRequest == null) {
            return searchVo;
        }
        String searchText = searchQueryRequest.getSearchText();
        String searchType = searchQueryRequest.getSearchType();
        int current = searchQueryRequest.getCurrent();
        int pageSize = searchQueryRequest.getPageSize();
        if (StringUtils.isBlank(searchType)) {
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource postDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.POST.getValue());
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource userDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.USER.getValue());
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<Image>> imageTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource imageDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.IMAGE.getValue());
                Page<Image> imagePage = imageDataSource.doSearch(searchText, current, pageSize);
                return imagePage;
            }, threadPoolTaskExecutor);

            CompletableFuture<Page<FileVo>> fileTask = CompletableFuture.supplyAsync(() -> {
                SearchDataSource fileDataSource = dataSourceRegistry.getDataSourceByType(SearchTypeEnum.FILE.getValue());
                Page<FileVo> filePage = fileDataSource.doSearch(searchText, current, pageSize);
                return filePage;
            }, threadPoolTaskExecutor);

            CompletableFuture.allOf(postTask, userTask, imageTask, fileTask);

            try {
                Page<PostVO> postVOPage = postTask.get();
                searchVo.setPostList(postVOPage.getRecords());
                Page<UserVO> userVOPage = userTask.get();
                searchVo.setUserList(userVOPage.getRecords());
                Page<Image> imagePage = imageTask.get();
                searchVo.setImageList(imagePage.getRecords());
                Page<FileVo> fileVoPage = fileTask.get();
                searchVo.setFileList(fileVoPage.getRecords());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            SearchDataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(searchType);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            List<?> records = page.getRecords();
            searchVo.setDataList(records);
        }
        return searchVo;
    }
}
```

##### 前端扩展

增加FileList.vue组件

```vue
<template>
  <a-table :columns="columns" pagination="false" :data-source="props.fileList">
    <template #headerCell="{ column }">
      <template v-if="column.key === 'fileName'">
        <span>
          <smile-outlined />
          {{ column.title }}
        </span>
      </template>
    </template>

    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'fileName'">
        <a>
          {{ record.fileName }}
        </a>
      </template>
      <template v-else-if="column.key === 'fileType'">
        <span>
          <a-tag :key="record.fileType" :color="green">
            {{ record.fileType.toUpperCase() }}
          </a-tag>
        </span>
      </template>
      <template v-else-if="column.key === 'createTime'">
        {{ formatTimeWithZone(record.createTime) }}
      </template>
      <template v-if="column.key === 'fileSizeFormat'">
        {{ record.fileSizeFormat }}
      </template>
      <template v-else-if="column.key === 'action'">
        <span>
          <!--          <a @click="down(record)">下载</a>-->
          <!--          <a-divider type="vertical" />-->
          <!--          <a>删除</a>-->
          <!--          <a-divider type="vertical" />-->
        </span>
      </template>
    </template>
  </a-table>
</template>
<script lang="ts" setup>
import { withDefaults, defineProps } from "vue";

interface Props {
  fileList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  fileList: () => [],
});

import { SmileOutlined, DownOutlined } from "@ant-design/icons-vue";
import { download } from "@/request/Search";
import { message } from "ant-design-vue";

const columns = [
  {
    title: "文件名",
    dataIndex: "fileName",
    key: "fileName",
  },
  {
    title: "上传时间",
    dataIndex: "createTime",
    key: "createTime",
  },
  {
    title: "文件类型",
    key: "fileType",
    dataIndex: "fileType",
  },
  {
    title: "文件大小",
    key: "fileSizeFormat",
    dataIndex: "fileSizeFormat",
  },
  {
    title: "下载次数",
    key: "downloadCount",
    dataIndex: "downloadCount",
  },
  {
    title: "操作",
    key: "action",
  },
];

// 方法2：手动调整时区
const formatTimeWithZone = (time: string) => {
  if (!time) return "";
  const date = new Date(time);
  date.setHours(date.getHours() + 8);
  return date.toLocaleString("zh-CN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    hour12: false,
  });
};

const down = (file: any) => {
  download({ id: file.id })
    .then((response: any) => {
      if (response.headers) {
        const contentType = response.headers["content-type"];
        const contentDisposition = response.headers["content-disposition"];

        // 如果是文件下载响应
        if (
          contentType?.includes("application/octet-stream") ||
          contentDisposition?.includes("attachment")
        ) {
          // 获取文件名
          const filename =
            contentDisposition
              ?.split(";")
              ?.find((n) => n.includes("filename="))
              ?.replace("filename=", "")
              ?.trim() || "download";

          // 创建下载
          const blob = new Blob([response.data]);
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement("a");
          link.href = url;
          link.download = filename;
          document.body.appendChild(link);
          link.click();
          link.remove();
          window.URL.revokeObjectURL(url);
        }
      }
    })
    .catch((error: any) => {
      console.error(error);
    });
};
</script>
```

路由增加fileUpLoad

```ts
import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import IndexPage from "../views/IndexPage.vue";
import FilePage from "../views/FilePage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "index",
    component: IndexPage,
  },
  {
    path: "/:category",
    component: IndexPage,
  },
  {
    path: "/fileUpLoad",
    component: FilePage,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
```

修改主页

```vue
<template>
  <div class="index-page">
    <a-input-search
      v-model:value="searchText"
      placeholder="input search searchText"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
    <MyDivider />
    <a-tabs v-model:activeKey="searchType" @change="onTabChange">
      <a-tab-pane key="post" tab="帖子">
        <PostList :postList="searchResultPostList" />
      </a-tab-pane>
      <a-tab-pane key="image" tab="图片">
        <ImageList :imageList="searchResultImageList" />
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :userList="searchResultUserList" />
      </a-tab-pane>
      <a-tab-pane key="file" tab="文件">
        <FileList :fileList="searchResultFileList" />
      </a-tab-pane>
    </a-tabs>

    <a-divider>分割线</a-divider>
    <a-button type="link" @click="toFilePage">上传文件</a-button>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  listImageByPage,
  listPostVOByPage,
  listUserVOByPage,
  searchAll,
} from "@/request/Search";
import PostList from "@/components/PostList.vue";
import ImageList from "@/components/ImageList.vue";
import UserList from "@/components/UserList.vue";
import FileList from "@/components/FileList.vue";
import { message } from "ant-design-vue";

const router = useRouter();
const route = useRoute();

//初始值
const searchType = route.params.category;
//最新的查询内容
const searchText = ref(route.query.searchText || "");

/**
 * 页面初始化查询参数
 */
const initSearchParams = {
  searchText: "",
  searchType: searchType,
  current: 1,
  pageSize: 10,
};

/**
 * url查询关键字
 */
const searchParams = ref(initSearchParams);

/**
 * 查询结果
 */
const searchResultPostList = ref([]);
const searchResultImageList = ref([]);
const searchResultUserList = ref([]);
const searchResultFileList = ref([]);

/**
 * 查询
 * @param value
 */
const onSearch = (value: string) => {
  router.push({
    query: {
      ...searchParams.value,
      searchText: value,
    },
  });
};

/**
 * tab切换
 * @param activeKey
 */
const onTabChange = (activeKey: string) => {
  router.push({
    path: `/${activeKey}`,
    query: {
      ...searchParams.value,
      searchType: activeKey,
    },
  });
};

/**
 * 加载数据
 */
const loadData = () => {
  searchAll(searchParams.value)
    .then((data: any) => {
      console.log(data);
      searchResultPostList.value = data.postList || [];
      searchResultImageList.value = data.imageList || [];
      searchResultUserList.value = data.userList || [];
      searchResultFileList.value = data.fileList || [];
    })
    .catch((error: any) => {
      console.error(error);
      message.error(error);
    });
};

/**
 * 分类查询加载数据
 */
const loadDataByType = () => {
  let searchType = searchParams.value.searchType;
  searchAll(searchParams.value)
    .then((data: any) => {
      console.log(data);
      if (searchType === "post") {
        searchResultPostList.value = data.dataList || [];
      } else if (searchType === "user") {
        searchResultUserList.value = data.dataList || [];
      } else if (searchType === "image") {
        searchResultImageList.value = data.dataList || [];
      } else if (searchType === "file") {
        searchResultFileList.value = data.dataList || [];
      }
    })
    .catch((error: any) => {
      console.error(error);
      message.error(error);
    });
};

/**
 * 监听url改变
 * 更新查询框参数
 */
watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    searchText: route.query.searchText,
    searchType: route.params.category,
  } as any;
  loadDataByType();
});

const toFilePage = () => {
  router.push({
    path: "/fileUpLoad",
  });
};

onMounted(() => {
  loadData();
});
</script>
```

![image-20241210215148411](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241210215148411.png)



#### 开发上传文件同步到es功能

##### 安装插件

通过命令行安装（推荐）

```cmd
1.进入 Elasticsearch 安装目录
2.使用 elasticsearch-plugin 命令安装
bin/elasticsearch-plugin install ingest-attachment
3.重启elasticsearch
# 如果是系统服务
sudo systemctl restart elasticsearch
# 或者直接重启
./bin/elasticsearch

查看是否安装成功
1.查看elasticsearch-7.17.12\plugins下是否存在ingest-attachment
2.查看已安装插件列表
bin/elasticsearch-plugin list

进入kibana调试控制台，通过 API 检查
GET /_nodes/plugins
```

可以手动加载插件再解压到plugins目录下

https://artifacts.elastic.co/downloads/elasticsearch-plugins/ingest-attachment/ingest-attachment-7.17.0.zip



##### 新增和es交互的实体FileEsDTO

尽量只留需要参与查询的字段，不经常变动的字段

```java
package com.xiaofei.site.search.model.dto.file;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件 ES 包装类
 **/
@Document(indexName = "file_v3")
@Data
public class FileEsDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 文件名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String fileName;

    /**
     * 文件类型
     */
    @Field(type = FieldType.Keyword)
    private String fileType;

    /**
     * 解析后的文本内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 文件描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String description;

    /**
     * 上传用户ID
     */
    @Field(type = FieldType.Long)
    private Long userId;

    /**
     * 业务类型
     */
    @Field(type = FieldType.Keyword)
    private String biz;

    /**
     * 下载次数
     */
    @Field(type = FieldType.Integer)
    private Integer downloadCount;

    /**
     * 创建时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updateTime;

    private Integer isDelete;


    private static final long serialVersionUID = 1L;
}
```



##### 新增文本抽取管道

字段就是存储文本的content

```json
#文本抽取管道
PUT /_ingest/pipeline/attachment
{
  "description": "Extract file content",
  "processors": [
    {
      "attachment": {
        "field": "content",
        "target_field": "attachment",
        "indexed_chars": -1
      }
    },
    {
      "remove": {
        "field": "content"
      }
    }
  ]
}
```

##### 新增es文档索引

```json
PUT /file_v3
{
  "mappings": {
    "properties": {
      "id": {
        "type": "long"
      },
      "fileName": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart",
        "fields":{
          "keyword":{
            "type":"keyword",
            "ignore_above":256
          }
        }
      },
      "fileType": {
        "type": "keyword"
      },
      "content": {
        "type": "binary"
      },
      "attachment": {
        "properties": {
          "content": {
            "type": "text",
            "analyzer": "ik_max_word",
            "search_analyzer": "ik_smart"
          },
          "content_type": {
            "type": "keyword"
          },
          "language": {
            "type": "keyword"
          },
          "title": {
            "type": "text"
          }
        }
      },
      "description": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "userId": {
        "type": "long"
      },
      "biz": {
        "type": "keyword"
      },
      "isDelete": {
        "type": "integer"
      },
      "createTime": {
        "type": "date"
      },
      "updateTime": {
        "type": "date"
      }
    }
  }
}
```

##### 修改文件上传方法

增加文本内容同步到es

```java
public interface FileService extends IService<FilePo> {
...
    Page<FileVo> searchFromEs(FileQueryRequest fileQueryRequest);
...
}
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FilePo> implements FileService {
@Resource
    private RestHighLevelClient restHighLevelClient;

 @Override
    public FilePo uploadFile(MultipartFile file, FileUploadBizEnum fileUploadBizEnum) {
        try {

           ...


            int insert = fileMapper.insert(filePo);
            if (insert > 0) {
                //上传es
                boolean esUpload = uploadFileToEs(filePo, dest);
                // 4. 删除临时文件
                dest.delete();

                if (!esUpload) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "文件上传失败");
                }
                return filePo;
            }
            return null;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "文件上传失败");
        }
    }
/**
     * 通过pipeline上传文件到es
     * @param filePo
     * @param file
     * @return
     */
    public boolean uploadFileToEs(FilePo filePo, File file) {
        try {
            // 1. 读取文件内容并转换为 Base64
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64Content = Base64.getEncoder().encodeToString(fileContent);

            // 2. 准备索引文档
            Map<String, Object> document = new HashMap<>();
            document.put("id", filePo.getId());
            document.put("fileName", filePo.getFileName());
            document.put("fileType", filePo.getFileType());
            document.put("content", base64Content);  // 使用 content 字段
            document.put("description", filePo.getDescription());
            document.put("userId", filePo.getUserId());
            document.put("biz", filePo.getBiz());
            document.put("createTime", filePo.getCreateTime());
            document.put("updateTime", filePo.getUpdateTime());

            // 3. 创建索引请求
            IndexRequest indexRequest = new IndexRequest("file_v3")
                    .id(filePo.getId().toString())
                    .setPipeline("attachment")
                    .source(document);

            // 4. 执行索引请求
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            return indexResponse.status() == RestStatus.CREATED
                    || indexResponse.status() == RestStatus.OK;

        } catch (Exception e) {
            log.error("上传文件到 ES 失败", e);
            return false;
        }
    }
    /**
     * 生成文件名（防止重复）
     *
     * @param originalFilename
     * @return
     */
    private String generateFileName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        return uuid + "." + extension;
    }

    /**
     * 计算文件MD5
     *
     * @param file
     * @return
     * @throws IOException
     */
    private String calculateMD5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }
}
```

上传一个测试文档

通过es查询，看看是否正常解析成文本

content存储着文本

![image-20241210220739746](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241210220739746.png)

新增es查询方法

```java
public interface FileService extends IService<FilePo> {
    Page<FileVo> searchFromEs(FileQueryRequest fileQueryRequest);
}
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FilePo> implements FileService {
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    @Override
    public Page<FileVo> searchFromEs(FileQueryRequest fileQueryRequest) {
        String searchText = fileQueryRequest.getSearchText();
        String fileName = fileQueryRequest.getFileName();
        String content = fileQueryRequest.getContent();
        // es 起始页为 0
        long current = fileQueryRequest.getCurrent() - 1;
        long pageSize = fileQueryRequest.getPageSize();
        String sortField = fileQueryRequest.getSortField();
        String sortOrder = fileQueryRequest.getSortOrder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));
        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("fileName", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("description", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按标题检索
        if (StringUtils.isNotBlank(fileName)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("fileName", fileName));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按内容检索
        if (StringUtils.isNotBlank(content)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", content));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withSorts(sortBuilder).build();
        SearchHits<FileEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, FileEsDTO.class);
        Page<FileVo> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        List<FilePo> resourceList = new ArrayList<>();
        // 查出结果后，从 db 获取最新动态数据
        if (searchHits.hasSearchHits()) {
            List<SearchHit<FileEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> fileIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());
            List<FilePo> fileList = baseMapper.selectBatchIds(fileIdList);
            if (fileList != null) {
                Map<Long, List<FilePo>> idPostMap = fileList.stream().collect(Collectors.groupingBy(FilePo::getId));
                fileIdList.forEach(fileId -> {
                    if (idPostMap.containsKey(fileId)) {
                        resourceList.add(idPostMap.get(fileId).get(0));
                    } else {
                        // 从 es 清空 db 已物理删除的数据
                        String delete = elasticsearchRestTemplate.delete(String.valueOf(fileId), FileEsDTO.class);
                        log.info("delete post {}", delete);
                    }
                });
            }
        }
        List<FileVo> fileVoList = new ArrayList<>();
        if (CollUtil.isNotEmpty(resourceList)) {
            for (FilePo filePo : resourceList) {
                FileVo fileVo = FilePoToVoUtils.poToVo(filePo);
                fileVoList.add(fileVo);
            }
        }
        page.setRecords(fileVoList);
        return page;
    }
}
```

修改数据源的查询方法

```java
@Service
public class FileDataSource implements SearchDataSource<FileVo>{

    @Resource
    private FileService fileService;

    @Override
    public Page<FileVo> doSearch(String searchText, int current, int pageSize) {

        ...
        Page<FileVo> fileVoPage = fileService.searchFromEs(fileQueryRequest);
        return fileVoPage;
    }
}
```

po转Vo工具

```java
package com.xiaofei.site.search.utils;

import com.xiaofei.site.search.model.entity.FilePo;
import com.xiaofei.site.search.model.vo.FileVo;
import org.springframework.stereotype.Component;

/**
 * @author tuaofei
 * @description TODO
 * @date 2024/12/6
 */
@Component
public class FilePoToVoUtils {

    public static FileVo poToVo(FilePo entity) {
        if (entity == null) {
            return null;
        }

        FileVo vo = new FileVo();
        vo.setId(entity.getId());
        vo.setBiz(entity.getBiz());
        vo.setFileName(entity.getFileName());
        vo.setFileType(entity.getFileType());
        vo.setFileSize(entity.getFileSize());
        vo.setFileSizeFormat(formatFileSize(entity.getFileSize()));
        vo.setFileExtension("");
        vo.setUserId(entity.getUserId());
        vo.setDownloadCount(entity.getDownloadCount());
        vo.setDescription(entity.getDescription());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        vo.setContent(entity.getContent());

        // 设置预览和下载URL
        vo.setPreviewUrl(generatePreviewUrl(entity));
        vo.setDownloadUrl(generateDownloadUrl(entity));

        // 设置权限
        vo.setCanPreview(checkPreviewPermission(entity));
        vo.setCanDownload(checkDownloadPermission(entity));

        return vo;
    }

    /**
     * 格式化文件大小
     */
    private static String formatFileSize(Long size) {
        if (size == null) {
            return "0B";
        }

        if (size < 1024) {
            return size + "B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2fKB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2fMB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2fGB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * 生成预览URL
     */
    private static String generatePreviewUrl(FilePo entity) {
        // 根据业务逻辑生成预览URL
        return "/api/file/preview/" + entity.getId();
    }

    /**
     * 生成下载URL
     */
    private static String generateDownloadUrl(FilePo entity) {
        // 根据业务逻辑生成下载URL
        return "/api/file/download/" + entity.getId();
    }

    /**
     * 检查预览权限
     */
    private static Boolean checkPreviewPermission(FilePo entity) {
        // 根据业务逻辑检查预览权限
        return true;
    }

    /**
     * 检查下载权限
     */
    private static Boolean checkDownloadPermission(FilePo entity) {
        // 根据业务逻辑检查下载权限
        return true;
    }
}
```

测试内容分词是否正常



logstash数据同步

```conf
input {
  jdbc {
    # JDBC 驱动配置
    jdbc_driver_library => "C:\Users\Administrator\AppData\Roaming\JetBrains\DataGrip2021.1\jdbc-drivers\MySQL ConnectorJ\8.0.25\mysql\mysql-connector-java\8.0.25\mysql-connector-java-8.0.25.jar"
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    jdbc_connection_string => "jdbc:mysql://localhost:3306/xiaofei_site_search?useSSL=false&serverTimezone=Asia/Shanghai"
    jdbc_user => "root"
    jdbc_password => "root"

    # 增量同步配置
    use_column_value => true
    tracking_column => "update_time"
    tracking_column_type => "timestamp"
    schedule => "*/5 * * * * *"  # 每5秒执行一次
    jdbc_default_timezone => "Asia/Shanghai"
    
    # 记录上次执行位置
    last_run_metadata_path => "D:/tools/Elastic_Search/logstash-7.17.12/data/plugins/last_run_metadata_file"

    # SQL 查询，匹配 FileEsDTO 的字段
    statement => "
      SELECT 
        id,
        fileName,
        fileType,
        content,
        description,
        userId,
        biz,
        downloadCount,
        createTime,
        updateTime,
        isDelete
      FROM file 
      WHERE update_time > :sql_last_value 
        AND update_time < NOW() 
      ORDER BY update_time DESC
    "
  }
}

filter {
  # 处理日期格式
  date {
    match => ["createTime", "yyyy-MM-dd HH:mm:ss"]
    target => "createTime"
  }
  date {
    match => ["updateTime", "yyyy-MM-dd HH:mm:ss"]
    target => "updateTime"
  }

  # 确保数值字段类型正确
  mutate {
    convert => {
      "id" => "integer"
      "userId" => "integer"
      "downloadCount" => "integer"
      "isDelete" => "integer"
    }
  }

  # 如果需要处理文件内容，可以添加 attachment 过滤器
  if [content] {
    attachment {
      source => "content"
      target => "attachment"
    }
  }
}

output {
  # 调试输出
  stdout { codec => rubydebug }

  # 输出到 Elasticsearch
  elasticsearch {
    hosts => ["127.0.0.1:9200"]
    index => "file_v3"  # 匹配 @Document(indexName = "file_v3")
    document_id => "%{id}"
    
    # 如果使用了 ingest pipeline，添加以下配置
    pipeline => "attachment"
  }
}
```







## 13.数据同步

定时任务，比如 1 分钟 1 次，找到 MySQL 中过去几分钟内（至少是定时周期的 2 倍）发生改变的数据，然后更新到 ES。

双写：写数据的时候，必须也去写 ES；更新删除数据库同理。（事务：建议先保证 MySQL 写成功，如果 ES 写失败了，可以通过定时任务 + 日志 + 告警进行检测和修复（补偿））

用 Logstash 数据同步管道（一般要配合 kafka 消息队列 + beats 采集器）：

Canal 监听 MySQL Binlog，实时同步



### logstash

传输和处理数据的管道

![image-20230814224919588](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230814224919588.png)

https://www.elastic.co/guide/en/logstash/7.17/getting-started-with-logstash.html

https://artifacts.elastic.co/downloads/logstash/logstash-7.17.9-windows-x86_64.zip

快速开始：https://bcdh.yuque.com/r/goto?url=https%3A%2F%2Fwww.elastic.co%2Fguide%2Fen%2Flogstash%2F7.17%2Frunning-logstash-windows.html



demo

```powershell
cd logstash-7.17.12
.\bin\logstash.bat -e "input { stdin { } } output { stdout {} }"
```

![image-20230814225546595](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230814225546595.png)

![image-20230814230036357](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230814230036357.png)



要把 MySQL 同步给 Elasticsearch

https://www.elastic.co/guide/en/logstash/7.17/plugins-inputs-jdbc.html

https://www.elastic.co/guide/en/logstash/7.17/plugins-outputs-elasticsearch.html

https://www.elastic.co/guide/en/logstash/7.17/plugins-filters-mutate.html

增量同步，过滤修改数据



不加filter的话

![image-20241203220605254](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203220605254.png)



sql_last_value存储在`logstash-7.17.12\data\plugins\inputs\jdbc`里面文件中，删掉可以全量同步



完整配置文件`logstash-mysqlToEs-post.conf`

```properties
# Sample Logstash configuration for creating a simple
# Beats -> Logstash -> Elasticsearch pipeline.

input {
  jdbc {
    jdbc_driver_library => "C:\Users\Administrator\AppData\Roaming\JetBrains\DataGrip2021.1\jdbc-drivers\MySQL ConnectorJ\8.0.25\mysql\mysql-connector-java\8.0.25\mysql-connector-java-8.0.25.jar"
    jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
    jdbc_connection_string => "jdbc:mysql://localhost:3306/xiaofei_site_search"
    jdbc_user => "root"
    jdbc_password => "root"
    statement => "SELECT * from post where updateTime > :sql_last_value and updateTime < now() order by updateTime desc"
    use_column_value => true
    tracking_column => "updatetime"
    tracking_column_type => "timestamp"
    parameters => { "isDelete" => 1 }
    schedule => "*/5 * * * * *"
    jdbc_default_timezone => "Asia/Shanghai"
  }
}

filter{
  mutate {
        rename => {
          "updatetime" => "updateTime"
          "userid" => "userId"
          "createtime" => "createTime"
          "isdelete" => "isDelete"
        }
        remove_field => [
          "thumbnum","favournum"
        ]
    }
}

output {
  stdout { codec => rubydebug }
  elasticsearch {
        hosts => "127.0.0.1:9200"
        index => "post_v2"
        document_id => "%{id}"
    }
}
```

在bin目录下执行`logstash -f ../config/logstash-mysqlToEs-post.conf`

可能找不到驱动包，修改路径

![image-20241203215149969](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203215149969.png)

![image-20241203221327857](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203221327857.png)



### 使用kibana创建看板查看数据

可修改为中文 版本>6.7

config下的kibana.yml

```yml
# Supported languages are the following: English - en , by default , Chinese - zh-CN .
i18n.locale: "zh-CN"
```



![image-20241203221652622](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203221652622.png)



![image-20241203221725530](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203221725530.png)

使用别名post

![image-20241203221808598](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203221808598.png)

使用更新时间作为时间

![image-20241203221830065](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203221830065.png)

创建完成

![image-20241203221927135](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203221927135.png)

进入dashboard

![image-20241203222018637](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203222018637.png)

![image-20241203225721352](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203225721352.png)

![image-20241203225837601](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20241203225837601.png)



### 订阅数据库流水的同步方式 Canal

[alibaba/canal: 阿里巴巴 MySQL binlog 增量订阅&消费组件 (github.com)](https://github.com/alibaba/canal)

原理：数据库每次修改时，会修改 binlog 文件，只要监听该文件的修改，就能第一时间得到消息并处理

canal：帮你监听 binlog，并解析 binlog 为你可以理解的内容。

它伪装成了 MySQL 的从节点，获取主节点给的 binlog，如图：

![image-20230814230526383](https://note-1259190304.cos.ap-chengdu.myqcloud.com/noteimage-20230814230526383.png)

快速开始：[QuickStart · alibaba/canal Wiki (github.com)](https://github.com/alibaba/canal/wiki/QuickStart)

如果 java 找不到，修改 startup.bat 脚本为你自己的 java home：

```cmd
set JAVA_HOME = 自己的jvm地址（如D:\install\java8\jdk）
set PATH=%JAVA_HOME%\bin;%PATH%
```

mysql8可能无法连接

[example.log报错日志 · Issue #3902 · alibaba/canal (github.com)](https://github.com/alibaba/canal/issues/3902)

```sql
ALTER USER 'canal_user'@'%' IDENTIFIED WITH mysql_native_password BY 'canal_user'; 
ALTER USER 'canal_user'@'%' IDENTIFIED BY 'canal_user' PASSWORD EXPIRE NEVER; 
FLUSH PRIVILEGES;
```
