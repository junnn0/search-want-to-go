import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import BootstrapVue from 'bootstrap-vue'
import ApiService from '@/common/api.service'
import TokenService from '@/common/token.service'
import {loadMessages} from './common/i18n'
import StoreConstant from '@/store/constant'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

Vue.prototype.$messages = loadMessages()

Vue.use(BootstrapVue)

ApiService.init()

new Vue({
  router,
  store,
  render: h => h(App),
  created() {
    let token = TokenService.getToken();
    if (token) {
      ApiService.setAuthHeader()
      ApiService.get('/user')
        .then(response => {
          if (response && response.data.header.isSuccessful) {
            this.$store.dispatch(StoreConstant.SET_USER, response.data.body)
            this.$router.push({name: 'Main'})
          }
        })
    }
  }
}).$mount('#app')
