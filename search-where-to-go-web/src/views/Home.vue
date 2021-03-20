<template>
  <div class="home">
    <h1>Home.vue</h1>
    <login/>
  </div>
</template>

<script>
import ApiService from "@/common/api.service"
import TokenService from '@/common/token.service'
import Login from '@/components/Login'
import StoreConstant from '@/store/constant'

export default {
  name: 'Home',
  components: {
    Login
  },
  created() {
    let token = TokenService.getToken();
    if (token) {
      ApiService.setHeader()
      ApiService.get('/user')
          .then(({data}) => {
            if (data.header.isSuccessful) {
              this.$store.dispatch(StoreConstant.SET_USER, data.body)
              this.$router.push({name: 'Main'})
            }
          })
    }
  }
}
</script>
