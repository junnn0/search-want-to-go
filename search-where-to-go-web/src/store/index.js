import Vue from 'vue'
import Vuex from 'vuex'
import StoreConstant from './constant'
import TokenService from '@/common/token.service'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userWithToken: {},
    isAuthenticated: false
  },
  mutations: {
    [StoreConstant.SET_USER](state, userWithToken) {
      state.userWithToken = userWithToken
      state.isAuthenticated = !!userWithToken
      TokenService.saveToken(userWithToken.token)
    },
    [StoreConstant.LOGOUT](state) {
      state.userWithToken = {}
      state.isAuthenticated = false
      TokenService.destroyToken()
    }
  },
  actions: {
    [StoreConstant.SET_USER](store, userWithToken) {
      store.commit(StoreConstant.SET_USER, userWithToken)
    },
    [StoreConstant.LOGOUT](store) {
      store.commit(StoreConstant.LOGOUT)
    }
  },
  getters: {
    getUser(state) {
      return state.userWithToken
    },
    getToken(state) {
      return state.userWithToken.token
    },
    isAuthenticated(state) {
      return state.isAuthenticated
    }
  },
  modules: {
  }
})
