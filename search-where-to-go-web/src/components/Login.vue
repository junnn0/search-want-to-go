<template>
  <div class="login">
    <h1>Login.vue</h1>
    <div>
      <label for="username">{{ $messages['common.username'] }}: </label>
      <input id="username" type="text" v-model="username" @keydown.enter="login"/>
    </div>
    <div>
      <label for="password">{{ $messages['common.password'] }}: </label>
      <input id="password" type="password" v-model="password" @keydown.enter="login"/>
    </div>
    <div>
      <button @click="login">Login</button>
    </div>
  </div>
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
