import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ApiService from "@/common/api.service"
import {loadMessages} from './common/i18n'

Vue.config.productionTip = false

Vue.prototype.$messages = loadMessages()

ApiService.init()

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
