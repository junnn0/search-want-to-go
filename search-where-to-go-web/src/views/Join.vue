<template>
  <div class="login">
    <div>
      <input id="username" type="text" v-model="username"/>
    </div>
    <div>
      <input id="password" type="password" v-model="password"/>
    </div>
    <div>
      <button @click="join">join</button>
    </div>
  </div>
</template>

<script>
import ApiService from "@/common/api.service"

export default {
  name: "Login",
  data() {
    return {
      username: '',
      password: '',
    }
  },
  methods: {
    join() {
      const user = {
        username: this.username,
        password: this.password
      }
      ApiService.post('/users', user)
          .then(response => {
            console.log(response)
            this.$router.push({name: 'Main'})
          })
    }
  }
}
</script>

<style scoped>

</style>
