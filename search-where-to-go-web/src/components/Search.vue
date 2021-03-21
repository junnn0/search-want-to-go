<template>
  <div id="search">
    <label for="searchBar">{{ $messages['common.search.word'] }}</label>
    <input id="searchBar" type="text" v-model="searchWord"/>
    <button @click="search" variant="danger">{{ $messages['common.search'] }}</button>

    <div>{{ `${$messages['common.total']} ${totalCount}` }}</div>
    <div class="mb-3" name="place-container">
      <div v-for="place in places" v-bind:key="place.name" style="display:block">
        <span name="place-col">{{ place.name }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import ApiService from "@/common/api.service"

export default {
  name: 'Search',
  data() {
    return {
      searchWord: '',
      places: []
    }
  },
  computed: {
    totalCount: function() {
      return this.places.length
    }
  },
  methods: {
    search() {
      if (this.searchWord) {
        ApiService.setAuthHeader()
        ApiService.get(`/v1.0/places?query=${this.searchWord}`)
            .then(({data}) => this.places = data.body)
      }
    }
  }
}
</script>

<style scoped>

</style>
