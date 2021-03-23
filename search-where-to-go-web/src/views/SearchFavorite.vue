<template>
  <div class="home">
    <h1>Favorite.vue</h1>
    <div>
      <span style="display: block" v-for="favoriteQuery in favoriteQueries" v-bind:key="favoriteQuery.query">{{ favoriteQuery }}</span>
    </div>
  </div>
</template>

<script>
import ApiService from "@/common/api.service"
import {mapGetters} from "vuex";

export default {
  name: 'SearchFavorite',
  data() {
    return {
      favoriteQueries: []
    }
  },
  computed: {
    ...mapGetters(['getToken'])
  },
  created() {
    let token = this.getToken;
    if (!token) {
      this.$router.push({name: 'Home'})
    } else {
      ApiService.setAuthHeader()
      ApiService.get('/v1.0/places/favorites')
          .then(response => {
            if (response && response.data.header.isSuccessful) {
              this.favoriteQueries = response.data.body
            }
          })
    }
  }
}
</script>
