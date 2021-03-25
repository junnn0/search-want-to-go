<template>
  <b-container id="search-favorite">
    <h4>{{ $messages['common.search.favorite'] }}</h4>
    <b-row align-h="center">
      <b-list-group class="w-75">
        <b-list-group-item v-for="(favoriteQuery, idx) in favoriteQueries" v-bind:key="idx">
          <b-row>
            <b-col cols="2">{{ idx + 1 }}</b-col>
            <b-col cols="6">{{ favoriteQuery.query }}</b-col>
            <b-col cols="4">{{ `${favoriteQuery.count} ${$messages['common.times']}` }}</b-col>
          </b-row>
        </b-list-group-item>
      </b-list-group>
    </b-row>
  </b-container>
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
