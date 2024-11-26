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
