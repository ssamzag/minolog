import {createRouter, createWebHistory} from "vue-router";
import HomeView from "../views/HomeView.vue";
import WriteView from "../views/WriteView.vue";
import ReadView from "../views/ReadView.vue";
import EditView from "../views/EditView.vue";
import LoginView from "../views/LoginView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeView,
        },
        {
            path: "/write",
            name: "write",
            component: WriteView
        },
        {
            path: "/read",
            name: "read",
            component: ReadView,
            props: true
        },
        {
            path: "/edit",
            name: "edit",
            component: EditView,
            props: true
        },
        {
            path: "/login",
            name: "login",
            component: LoginView,
            props: true
        }
    ],
});

export default router;
