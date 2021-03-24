<template>
  <b-container id="app" fluid="true" class="mt-xl-5">
    <span v-if="!isAuthenticated">
      <b-nav align="center">
          <b-nav-item active>
            <router-link to="/">{{ $messages['common.home'] }}</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/join">{{ $messages['common.join'] }}</router-link>
          </b-nav-item>
      </b-nav>
    </span>
    <span v-else>
      <b-button variant="outline-danger" @click="logout" class="float-md-right">{{ $messages['common.logout'] }}</b-button>
      <b-nav align="center">
        <b-nav-item active>
          <router-link to="/main">{{ $messages['common.home'] }}</router-link>
        </b-nav-item>
        <b-nav-item>
          <router-link to="/histories">{{ $messages['common.search.history'] }}</router-link>
        </b-nav-item>
        <b-nav-item>
          <router-link to="/favorites">{{ $messages['common.search.favorite'] }}</router-link>
        </b-nav-item>
      </b-nav>
    </span>
    <router-view/>
  </b-container>
</template>

<script>
import {mapGetters} from "vuex";
import StoreConstant from "@/store/constant";

export default {
  name: 'App',
  computed: {
    ...mapGetters(["isAuthenticated"])
  },
  methods: {
    logout() {
      this.$store.dispatch(StoreConstant.LOGOUT)
      this.$router.push({name: 'Home'})
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}
</style>
