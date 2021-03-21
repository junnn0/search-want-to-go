<template>
  <div class="home">
    <h1>Home.vue</h1>
    <login/>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import ApiService from "@/common/api.service"
import TokenService from '@/common/token.service'
import Login from '@/components/Login'
import StoreConstant from '@/store/constant'

export default {
  name: 'Home',
  components: {
    Login
  },
  computed: {
    ...mapGetters(["getUser"]),
  },
  created() {
    let token = TokenService.getToken();
    console.log('home:', token)
    if (token) {
      console.log('user:', this.getUser.username)
      ApiService.setAuthHeader()
      ApiService.get('/user')
          .then(({header, body}) => {
            console.log('Home', header, body)
            if (header.isSuccessful) {
              this.$store.dispatch(StoreConstant.SET_USER, body)
              this.$router.push({name: 'Main'})
            }
          })
    }
  }
}
</script>
