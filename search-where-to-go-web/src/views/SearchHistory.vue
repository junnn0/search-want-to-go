<template>
  <b-container id="search-history">
    <h4>{{ $messages['common.search.history'] }}</h4>
    <b-row align-h="center">
      <b-list-group class="w-75">
        <b-list-group-item v-for="history in histories" v-bind:key="history.id">
          <b-row>
            <b-col cols="8">{{ history.query }}</b-col>
            <b-col cols="4" style="font-size: 13px">{{ new Date(history.createDatetime).toLocaleString() }}</b-col>
          </b-row>
        </b-list-group-item>
      </b-list-group>
    </b-row>
    <b-row align-h="center" class="mt-4">
      <b-pagination
          v-model="pageNum"
          :total-rows="totalHistoryCount"
          :per-page="pageSize"
          @page-click="getHistories"
      ></b-pagination>
    </b-row>
  </b-container>
</template>

<script>
import ApiService from "@/common/api.service"
import {mapGetters} from "vuex";

export default {
  name: 'SearchHistory',
  data() {
    return {
      histories: [],
      totalHistoryCount: 10,
      pageSize: 10,
      pageNum: 1
    }
  },
  computed: {
    ...mapGetters(['getToken'])
  },
  created() {
    const token = this.getToken;
    if (token) {
      this.getHistories({}, 1)
    } else {
      this.$router.push({name: 'Home'})
    }
  },
  methods: {
    getHistories(event, pageNum) {
      ApiService.setAuthHeader()
      ApiService.get(`/v1.0/places/histories?pageSize=${this.pageSize}&pageNum=${pageNum}`)
          .then(response => {
            if (response && response.data.header.isSuccessful) {
              this.histories = response.data.body.data
              this.totalHistoryCount = response.data.body.totalCount
            }
          })
    }
  }
}
</script>

<style scoped>

</style>
