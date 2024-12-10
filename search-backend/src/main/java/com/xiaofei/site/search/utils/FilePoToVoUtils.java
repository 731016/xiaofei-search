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
