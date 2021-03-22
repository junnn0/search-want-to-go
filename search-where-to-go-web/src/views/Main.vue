<template>
  <div class="home">
    <h1>Main.vue</h1>
    <button @click="logout">logout</button>
    <search />
  </div>
</template>

<script>
import Search from '@/components/Search'
import StoreConstant from '@/store/constant'
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
  },
  methods: {
    logout() {
      this.$store.dispatch(StoreConstant.LOGOUT)
      this.$router.push({name: 'Home'})
    }
  }
}
</script>
