<template>
  <b-container class="login mt-xl-5" fluid="true">
    <b-row align-h="center" class="mt-lg-3">
      <b-col cols="3">
        <label style="font-size: 25px">{{ $messages['common.username'] }}</label>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="3">
        <b-form-input size="lg" type="text" :autofocus="true" v-model="username" @keydown.enter="join" placeholder="Enter your username"/>
      </b-col>
    </b-row>
    <b-row align-h="center" class="mt-lg-3">
      <b-col cols="3">
        <label style="font-size: 25px">{{ $messages['common.password'] }}</label>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="3">
        <b-form-input size="lg" type="password" v-model="password" @keydown.enter="join" placeholder="Enter your password"/>
      </b-col>
    </b-row>
    <b-row align-h="center" class="mt-xl-4">
      <b-col cols="3">
        <b-button variant="outline-success" @click="join">{{ $messages['common.join'] }}</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import ApiService from "@/common/api.service"

export default {
  name: 'Join',
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
      if (!this.username || !this.password) {
        alert(this.$messages['errors.common.join']);
      } else {
        ApiService.post('/users', user)
            .then(({data}) => {
              if (data.header.isSuccessful) {
                this.$router.push({name: 'Main'})
              }
            })
      }
    }
  }
}
</script>

<style scoped>

</style>
