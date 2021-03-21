<template>
  <div class="search-history">
    <h2>SearchHistory.vue</h2>
    <div>
      <span style="display: block" v-for="history in histories" v-bind:key="history.id">{{ history }}</span>
    </div>
  </div>
</template>

<script>
import ApiService from "@/common/api.service"
import TokenService from '@/common/token.service'

export default {
  name: 'SearchHistory',
  data() {
    return {
      histories: [],
      pageSize: 10,
      pageNum: 0
    }
  },
  created() {
    const token = TokenService.getToken();
    if (token) {
      ApiService.setAuthHeader()
      ApiService.get(`/v1.0/places/histories?pageSize=${this.pageSize}&pageNum=${this.pageNum}`)
          .then(({body}) => this.histories = body)
    } else {
      this.$router.push({name: 'Home'})
    }
  }
}
</script>

<style scoped>

</style>
