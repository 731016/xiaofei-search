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
