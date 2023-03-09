import { createRouter, createWebHashHistory } from 'vue-router'
import GYWe from "../views/QT/gyWe/index.vue"

const routes = [{
        path: '/',
        name: '后台首页',
        component: () =>
            import ('../layout'),
        redirect: 'index',
        children: [{
                path: '/index',
                name: '首页',
                component: () =>
                    import ('../views/index/index')
            },
            {
                path: '/userCenter',
                name: '个人中心',
                component: () =>
                    import ('../views/userCenter/index')
            }
        ]
    },
    {
        path: '/null',
        name: '',
        component: () =>
            import ('../layoutQT'),
        redirect: 'index',
        children: [{
                path: '/newfoot',
                name: 'newfoot',
                component: () =>
                    import ('../views/QT/newfoot/index.vue')
            },
            {
                path: '/index',
                name: '首页',
                component: () =>
                    import ('../views/QT/shouye/index.vue')
            },

            {
                path: '/gyWe',
                name: 'GYWe',
                component: () =>
                    import ('../views/QT/gyWe/index.vue')
            },
            {
                path: '/xiangqing',
                name: '美食详情',
                component: () =>
                    import ('../views/QT/xinagqing.vue')
            }

        ]
    },

    {
        path: '/login',
        name: 'login',
        component: () =>
            import ('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () =>
            import ('../views/register.vue')
    },
    {
        path: '/about',
        name: 'about',
        component: () =>
            import ( /* webpackChunkName: "about" */ '../views/AboutView.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router