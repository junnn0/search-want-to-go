import axios from 'axios'
import store from '../store'
import StoreConstant from '@/store/constant'
import Vue from 'vue'
import {loadMessages} from '@/common/i18n'

const messages = loadMessages()

function showErrorMessage(message) {
    store.dispatch(StoreConstant.SHOW_ALERT_MODAL, {
        isShowAlertModal: true,
        alertModalTitle: 'Error',
        alertModalMessage: message
    })
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

function resolveLogin() {
    document.location = '/login'
}

axios.interceptors.request.use(function (request) {
    request.headers.common['Access-Control-Allow-Origin'] = '*'
    return request
}, function (error) {
    Vue.$log.error(error)
    return Promise.reject(error)
})

axios.interceptors.response.use(function (response) {
    if (response.data.header && !response.data.header.isSuccessful) {
        if (response.data.header.resultCode === -1000) {
            resolveLogin()
            return
        }

        showErrorMessage(getErrorMessage(response.data.header.resultCode))
        return new Promise(() => {
        })
    } else {
        return response
    }
}, function (error) {
    if (Object.prototype.hasOwnProperty.call(error.config, 'errorHandle') && error.config.errorHandle === false) {
        return Promise.reject(error)
    }

    Vue.$log.error(error)
    showErrorMessage(messages['errors.common.network.failure'])
})

axios.defaults.spinner = true
axios.defaults.withCredentials = true

const getRuntimeConfig = async () => {
    const runtimeConfig = await fetchConfig()
    if (!runtimeConfig.apiBaseUrl) {
        Vue.$log.error('invalid apiBaseUrl. check runtime config')
        showErrorMessage('error')
    } else {
        axios.defaults.baseURL = runtimeConfig.apiBaseUrl
    }
}

function fetchConfig() {
    return fetch('/config/runtimeConfig.json')
        .then(response => response.json())
}

getRuntimeConfig()

export const http = axios
