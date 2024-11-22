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
