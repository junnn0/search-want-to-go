<template>
  <b-container id="search">
    <b-row>
      <b-input-group :prepend="this.$messages['common.search.word']" class="mt-3">
        <b-form-input v-model="searchWord" @keydown.enter="search" :autofocus="true"></b-form-input>
        <b-input-group-append>
          <b-button variant="outline-success" @click="search">{{ $messages['common.search'] }}</b-button>
        </b-input-group-append>
      </b-input-group>
    </b-row>
    <span v-show="totalCount > 0">
      <b-row class="mt-5">
        <b-col>
          <h5>{{ `${$messages['common.total']} ${totalCount} ${$messages['common.each']}`}}</h5>
        </b-col>
      </b-row>
      <b-row class="mt-2">
        <b-list-group class="w-100">
          <b-list-group-item v-for="place in places" v-bind:key="place.name">
            {{ place.name }}
          </b-list-group-item>
        </b-list-group>
      </b-row>
    </span>
  </b-container>
</template>

<script>
import ApiService from '@/common/api.service'

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
      } else {
        this.places = []
      }
    }
  }
}
</script>

<style scoped>

</style>
