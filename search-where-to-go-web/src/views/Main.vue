<template>
  <div class="home">
    <h1>Main.vue</h1>
    <button @click="logout">logout</button>
  </div>
</template>

<script>
import TokenService from '@/common/token.service'

export default {
  name: 'Home',
  components: {
  },
  created() {
    let token = TokenService.getToken();
    if (!token) {
      this.$router.push({name: 'Home'})
    }
  },
  methods: {
    logout() {
      TokenService.destroyToken()
      this.$router.push({name: 'Home'})
    }
  }
}
</script>
