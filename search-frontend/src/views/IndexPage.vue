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
  searchAll,
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
  loadAll();
  // if ("post" === activeTabKey) {
  // }
  // if ("user" === activeTabKey) {
  // }
  // if ("image" === activeTabKey) {
  // }
};

/*const searchAll = (activeTabKey: string) => {
  listPostVOByPage(searchParams.value)
    .then((data: { records: never[] }) => {
      console.log(data.records);
      searchResultPostList.value = data.records;
    })
    .catch((error: any) => {
      console.error(error);
    });
  listUserVOByPage({
    ...searchParams.value,
    userName: searchParams.value.searchText,
  })
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
};*/

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
