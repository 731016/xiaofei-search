import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import IndexPage from "../views/IndexPage.vue";
import FilePage from "../views/FilePage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "index",
    component: IndexPage,
  },
  {
    path: "/:category",
    component: IndexPage,
  },
  {
    path: "/fileUpLoad",
    component: FilePage,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
