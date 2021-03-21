<template>
  <div class="home">
    <h1>Favorite.vue</h1>
    <div>
      <span style="display: block" v-for="favoriteQuery in favoriteQueries" v-bind:key="favoriteQuery.query">{{ favoriteQuery }}</span>
    </div>
  </div>
</template>

<script>
import TokenService from '@/common/token.service'
import ApiService from "@/common/api.service"

export default {
  name: 'SearchFavorite',
  data() {
    return {
      favoriteQueries: []
    }
  },
  created() {
    let token = TokenService.getToken();
    if (!token) {
      this.$router.push({name: 'Home'})
    } else {
      ApiService.setAuthHeader()
      ApiService.get('/v1.0/places/favorites')
          .then(({body}) => this.favoriteQueries = body)
    }
  }
}
</script>
