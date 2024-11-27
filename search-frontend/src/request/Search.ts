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

/**
 * 聚合查询
 * @param params
 */
export const searchAll = (params: any) =>
  ReqAxios("post", "/search/all", params);
