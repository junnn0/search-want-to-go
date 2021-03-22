<template>
  <div id="app">
    <div id="nav">
      <span v-if="!isAuthenticated">
        <router-link to="/">{{ $messages['common.home'] }}</router-link> |
        <router-link to="/join">{{ $messages['common.join'] }}</router-link>
      </span>
      <span v-else>
        <router-link to="/main">{{ $messages['common.home'] }}</router-link> |
        <router-link to="/histories">{{ $messages['common.search.history'] }}</router-link> |
        <router-link to="/favorites">{{ $messages['common.search.favorite'] }}</router-link>
      </span>
    </div>
    <router-view/>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: 'App',
  computed: {
    ...mapGetters(["isAuthenticated"])
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
