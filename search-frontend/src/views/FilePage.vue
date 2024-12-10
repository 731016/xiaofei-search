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
