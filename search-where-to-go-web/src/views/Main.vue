<template>
  <b-container class="home">
    <h1>Main.vue</h1>
    <search/>
  </b-container>
</template>

<script>
import Search from '@/components/Search'

import {mapGetters} from "vuex";

export default {
  name: 'Home',
  components: {
    Search
  },
  computed: {
    ...mapGetters(['getToken'])
  },
  created() {
    let token = this.getToken;
    if (!token) {
      this.$router.push({name: 'Home'})
    }
  }
}
</script>
