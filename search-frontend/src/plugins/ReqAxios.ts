import axios from "axios";
import { message } from "ant-design-vue";

const reqAxios = axios.create({
  baseURL: "http://localhost:8101/api",
  timeout: 10000,
  headers: {},
});

// 请求拦截
reqAxios.interceptors.request.use(
  (config) => {
    // let token = localStorage.getItem("token") || "";
    // config.headers["Authorization"] = token;
    return config;
  },
  (err) => {
    Promise.reject(err);
  }
);

// 添加响应拦截器
reqAxios.interceptors.response.use(
  function (response) {
    // 方法1：通过 Content-Type 判断
    const contentType = response.headers["content-type"];
    // 方法2：通过 Content-Disposition 判断
    const contentDisposition = response.headers["content-disposition"];

    // 如果是文件下载类型，直接返回整个响应
    if (
      // 检查常见的文件下载 Content-Type
      (contentType &&
        (contentType.includes("application/octet-stream") ||
          contentType.includes("application/vnd.ms-excel") ||
          contentType.includes("application/x-msdownload") ||
          contentType.includes("application/pdf") ||
          contentType.includes("application/zip") ||
          contentType.includes("application/x-zip-compressed") ||
          contentType.includes("binary/octet-stream"))) ||
      // 检查 Content-Disposition 是否包含 attachment 或 filename
      (contentDisposition &&
        (contentDisposition.includes("attachment") ||
          contentDisposition.includes("filename")))
    ) {
      return response;
    }

    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    const data = response.data;
    if (data.code === 0) {
      return data.data;
    } else {
      // 状态码不为 0 时，抛出错误
      console.error(data.message);
      message.error(data.message);
      return Promise.reject({
        message: data.message || "请求失败",
        code: data.code,
        data: data.data,
      });
    }
    return data;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    if (error && error.response) {
      switch (error.response.status) {
        case 400:
          error.message = "请求错误(400)";
          break;
        case 401:
          error.message = "未授权，请重新登录(401)";
          break;
        case 403:
          error.message = "拒绝访问(403)";
          break;
        case 404:
          error.message = "请求出错(404)";
          break;
        case 408:
          error.message = "请求超时(408)";
          break;
        case 4003:
          error.message = "token失效,请重新登录";
          localStorage.removeItem("token");
          location.reload();
          break;
        case 500:
          error.message = "服务器错误(500)";
          break;
        case 501:
          error.message = "服务未实现(501)";
          break;
        case 502:
          error.message = "网络错误(502)";
          break;
        case 503:
          error.message = "服务不可用(503)";
          break;
        case 504:
          error.message = "网络超时(504)";
          break;
        case 505:
          error.message = "HTTP版本不受支持(505)";
          break;
        default:
          error.message = "连接出错" + error.response.status;
      }
    } else {
      error.message = "连接服务器失败!";
    }
    return Promise.reject(error);
  }
);
/**
 * 使用es6的export default导出了一个函数，导出的函数代替axios去帮我们请求数据，
 * 函数的参数及返回值如下：
 * @param {String} method  请求的方法：get、post、delete、put
 * @param {String} url     请求的url:
 * @param {Object} data    请求的参数
 * @param {Object} config        请求配置
 * @returns {Promise}     返回一个promise对象，其实就相当于axios请求数据的返回值
 */
export default function (method: string, url: string, data: any, config = {}) {
  method = method.toLowerCase();
  if (method == "post") {
    return reqAxios.post(url, data);
  } else if (method == "get") {
    return reqAxios.get(url, { params: data });
  } else if (method == "file") {
    return reqAxios.post(url, data, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
      ...config, // 合并自定义配置
    });
  } else if (method == "download") {
    return reqAxios.post(url, data, {
      responseType: "blob",
      headers: {
        "Content-Type": "multipart/form-data",
      },
      ...config,
    });
  } else {
    console.error("未知的method" + method);
    return false;
  }
}
