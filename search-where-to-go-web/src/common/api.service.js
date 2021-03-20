import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios"
import TokenService from '@/common/token.service'
import {loadMessages} from '@/common/i18n'

const messages = loadMessages()
const runtimeConfig = fetchConfig()

const ApiService = {
  init() {
    Vue.use(VueAxios, axios);
    this.setRequestInterceptor()
    this.setResponseInterceptor()
    axios.defaults.withCredentials = true
    if (runtimeConfig.apiBaseUrl) {
      Vue.axios.defaults.baseURL = runtimeConfig.apiBaseUrl
    } else {
      Vue.axios.defaults.baseURL = 'http://localhost:8080'
    }
  },

  setRequestInterceptor() {
    axios.interceptors.request.use(function (request) {
      request.headers.common['Access-Control-Allow-Origin'] = '*'
      return request
    }, function (error) {
      Vue.$log.error(error)
      return Promise.reject(error)
    })
  },

  setResponseInterceptor() {
    axios.interceptors.response.use(function (response) {
      if (response.data.header && !response.data.header.isSuccessful) {
        if (response.data.header.resultCode === 1000) {
          resolveLogin()
          return
        }

        showErrorMessage(getErrorMessage(response.data.header.resultCode))
        return new Promise(() => {
        })
      } else {
        return response.data
      }
    }, function (error) {
      if (Object.prototype.hasOwnProperty.call(error.config, 'errorHandle') && error.config.errorHandle === false) {
        return Promise.reject(error)
      }

      Vue.$log.error(error)
      showErrorMessage(messages['errors.common.network.failure'])
    })
  },

  setHeader() {
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
  },

  update(resource, params) {
    return Vue.axios.put(`${resource}`, params);
  },

  put(resource, params) {
    return Vue.axios.put(`${resource}`, params);
  },

  delete(resource) {
    return Vue.axios.delete(resource).catch(error => {
      throw new Error(`[RWV] ApiService ${error}`);
    });
  }
};

function fetchConfig() {
  return fetch('/config/runtimeConfig.json')
    .then(response => response.json())
}

function resolveLogin() {
  document.location = '/login'
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
