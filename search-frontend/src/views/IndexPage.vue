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
