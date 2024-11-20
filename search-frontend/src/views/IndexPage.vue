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
