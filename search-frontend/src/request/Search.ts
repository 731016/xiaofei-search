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
