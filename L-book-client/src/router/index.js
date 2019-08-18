import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../pages/Home/Home.vue'
import Login from '../pages/Login/Login.vue'
import Books from '../pages/Books/Books.vue'
import Search from '../pages/Search/Search.vue'
import Orders from '../pages/Orders/Orders.vue'
import Statistics from '../pages/Statistics/Statistics.vue'
import Users from '../pages/Users/Users.vue'
import About from '../pages/About/About.vue'


Vue.use(VueRouter)

export default new VueRouter({
    // 所有路由
    routes: [
        {
            path: '/login',
            component: Login,
            meta: {
                showNavDetail: false,
            }
        },
        {
            path: '/home',
            component: Home,
            meta: {
                showNavDetail: true,
            }
        },
        {
            path: '/books',
            component: Books,
            meta: {
                showNavDetail: true
            }
        },
        {
            path: '/search',
            component: Search,
            meta: {
                showNavDetail: true
            }
        },
        {
            path: '/orders',
            component: Orders,
            meta: {
                showNavDetail: true
            }
        },
        {
            path: '/statistics',
            component: Statistics,
            meta: {
                showNavDetail: true
            }
        },
        {
            path: '/users',
            component: Users,
            meta: {
                showNavDetail: true
            }
        },
        {
            path: '/about',
            component: About,
            meta: {
                showNavDetail: true
            }
        },
        {
            path: '/',
            redirect: '/home'
        },
    ]
})

