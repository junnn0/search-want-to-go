<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">{{ $messages['common.home'] }}</router-link> |
      <router-link to="/join">{{ $messages['common.join'] }}</router-link> |
      <router-link to="/histories">{{ $messages['common.search.history'] }}</router-link> |
      <router-link to="/favorites">{{ $messages['common.search.favorite'] }}</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
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

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
