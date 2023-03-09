import router from '@/router';
import { createStore } from 'vuex'

export default createStore({
    state: {
        data: '',
        //路由标识
        hasRoutes: false,
        editableTabsValue: '/index',
        editableTabs: [{
            title: '首页',
            name: '/index'
        }]
    },
    getters: {
        GET_TOKEN: state => {
            return sessionStorage.getItem("token")
        },
        GET_MENULIST: state => {
            return JSON.parse(sessionStorage.getItem("menuList"));
        },
        GET_USERINFO: state => {
            return JSON.parse(sessionStorage.getItem("userInfo"));
        }
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            sessionStorage.setItem("token", token)
        },
        SET_MENULIST: (state, menuList) => {
            sessionStorage.setItem("menuList", JSON.stringify(menuList));
        },
        SET_USERINFO: (state, userInfo) => {
            sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
        },
        SET_ROUTES_STATE: (state, hasRoutes) => {
            state.hasRoutes = hasRoutes;
        },
        ADD_TABS: (state, tab) => {
            if (state.editableTabs.findIndex(e => e.name === tab.path) === -1) {
                state.editableTabs.push({
                    title: tab.name,
                    name: tab.path
                })
            }
            state.editableTabsValue = tab.path
        },
        RESTE_TABS: (state) => {
            state.editableTabsValue = '/index',
                state.editableTabs = [{
                    title: '首页',
                    name: '/index'
                }]
        },
        setData(state, data) {
            state.data = data
        }
    },
    actions: {
        //退出登录
        logout() {
            window.sessionStorage.clear();
            router.replace("/login");
        },
        updateData({ commit }, data) {
            commit('setData', data)
        }
    },

    modules: {}
})