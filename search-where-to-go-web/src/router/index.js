import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/join',
    name: 'Join',
    component: () => import('@/views/Join.vue')
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import('@/views/Main.vue')
  },
  {
    path: '/histories',
    name: 'SearchHistory',
    component: () => import('@/views/SearchHistory')
  },
  {
    path: '/favorites',
    name: 'SearchFavorite',
    component: () => import('@/views/SearchFavorite')
  }
]

const router = new VueRouter({
  routes
})

export default router
