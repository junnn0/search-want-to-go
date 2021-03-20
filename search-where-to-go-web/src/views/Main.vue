<template>
  <div class="home">
    <h1>Main.vue</h1>
    <button @click="logout">logout</button>
    <search />
  </div>
</template>

<script>
import TokenService from '@/common/token.service'
import Search from '@/components/Search'

export default {
  name: 'Home',
  components: {
    Search
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
