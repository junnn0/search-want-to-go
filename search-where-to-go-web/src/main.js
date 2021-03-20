import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {http} from './common/http'
import {loadMessages, getUserLanguage} from './common/i18n'
import StoreConstant from './store/constant'

Vue.config.productionTip = false

Vue.prototype.$http = http
Vue.prototype.$lang = getUserLanguage()
Vue.prototype.$messages = loadMessages()
Vue.prototype.$alert = showAlertModal

function showAlertModal(title, message) {
  this.$log.debug('showAlertModal()', title, message)
  this.$store.dispatch(StoreConstant.SHOW_ALERT_MODAL, {
    isShowAlertModal: true,
    alertModalTitle: title,
    alertModalMessage: message
  })
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
