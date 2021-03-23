import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import TokenService from '@/common/token.service'
import {loadMessages} from '@/common/i18n'

const messages = loadMessages()
const runtimeConfig = fetchConfig()

const ApiService = {
  init() {
    Vue.use(VueAxios, axios);
    this.setRequestInterceptor()
    this.setResponseInterceptor()
    Vue.axios.defaults.withCredentials = true
    if (runtimeConfig.apiBaseUrl) {
      Vue.axios.defaults.baseURL = runtimeConfig.apiBaseUrl
    } else {
      Vue.axios.defaults.baseURL = 'http://localhost:8080'
    }
  },

  setRequestInterceptor() {
    Vue.axios.interceptors.request.use(function (request) {
      request.headers.common['Access-Control-Allow-Origin'] = '*'
      return request
    }, function (error) {
      return Promise.reject(error)
    })
  },

  setResponseInterceptor() {
    Vue.axios.interceptors.response.use(function (response) {
      if (response.data && !response.data.header.isSuccessful) {
        if (response.data.header.resultCode === 1000) {
          resolveLogout()
          return
        }

        showErrorMessage(getErrorMessage(response.data.header.resultCode))
        return new Promise(() => {
        })
      } else {
        return response
      }
    }, function (error) {
      if (error.response && error.response.status) {
        showErrorMessage(messages[`errors.http.status.${error.response.status}`])
        resolveLogout()
        document.location.reload()
      } else {
        showErrorMessage(messages['errors.common.network.failure'])
      }
    })
  },

  setAuthHeader() {
    Vue.axios.defaults.headers.common[
      "Authorization"
      ] = `Token ${TokenService.getToken()}`
  },

  get(resource) {
    return Vue.axios.get(resource).catch(error => {
      throw new Error(`ApiService ${error}`);
    });
  },

  post(resource, params) {
    return Vue.axios.post(`${resource}`, params);
  }
};

function fetchConfig() {
  return fetch('/config/runtimeConfig.json')
    .then(response => response.json())
}

function resolveLogout() {
  TokenService.destroyToken()
}

function showErrorMessage(message) {
  alert(message)
}

function getErrorMessage(code) {
  let message = messages['errors.common.server.unknown']
  if (messages['errors.code.' + code] !== undefined) {
    message = messages['errors.code.' + code]
  } else if (messages[code] !== undefined) {
    message = messages[code]
  }
  return message
}

export default ApiService
