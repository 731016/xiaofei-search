<template>
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta :description="item.content">
          <template #title>
            <a href="http://xiaofei.site">{{ item.title }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="item.userAvatar || defaultImage" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup lang="ts">
import { withDefaults, defineProps } from "vue";
import image404 from "@/assets/404.jpg";

interface Props {
  postList: any[];
}

const defaultImage = image404;

const props = withDefaults(defineProps<Props>(), {
  postList: () => [],
});
</script>

<style scoped></style>
