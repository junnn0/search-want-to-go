<template>
  <b-container class="login mt-xl-5" fluid="true">
    <b-row align-h="center" class="mt-lg-3">
      <b-col cols="3">
        <label style="font-size: 25px">{{ $messages['common.username'] }}</label>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="3">
        <b-form-input size="lg" type="text" :autofocus="true" v-model="username" @keydown.enter="login" placeholder="Enter your username"/>
      </b-col>
    </b-row>
    <b-row align-h="center" class="mt-lg-3">
      <b-col cols="3">
        <label style="font-size: 25px">{{ $messages['common.password'] }}</label>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="3">
        <b-form-input size="lg" type="password" v-model="password" @keydown.enter="login" placeholder="Enter your password"/>
      </b-col>
    </b-row>
    <b-row align-h="center" class="mt-xl-4">
      <b-col cols="3">
        <b-button variant="outline-success" @click="login">{{ $messages['common.login'] }}</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import ApiService from '@/common/api.service'
import StoreConstant from '@/store/constant'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login() {
      const user = {
        username: this.username,
        password: this.password
      }
      ApiService.post('/users/login', user)
          .then(({data}) => {
            this.$store.dispatch(StoreConstant.SET_USER, data.body)
            this.$router.push({name: 'Main'})
          })
    }
  }
}
</script>

<style scoped>

</style>
