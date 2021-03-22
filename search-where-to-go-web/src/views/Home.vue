<template>
  <div class="home">
    <h1>Home.vue</h1>
    <login/>
  </div>
</template>

<script>
import Login from '@/components/Login'
import {mapGetters} from "vuex";

export default {
  name: 'Home',
  components: {
    Login
  },
  computed: {
    ...mapGetters(['getToken'])
  },
  mounted() {
    let token = this.getToken;
    if (token) {
      this.$router.push({name: 'Main'})
    }
  }
}
</script>
