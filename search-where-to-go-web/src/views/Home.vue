<template>
  <b-container class="mt-lg-5" fluid="true">
    <b-row>
      <b-col align-self="center">
        <h1>{{ $messages['common.title'] }}</h1>
        <login/>
      </b-col>
    </b-row>
  </b-container>
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
