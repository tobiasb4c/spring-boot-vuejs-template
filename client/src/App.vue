<script setup>
import { RouterLink, RouterView } from 'vue-router'
import NavBar from './components/NavBar.vue'
</script>

<template>
  <header>
    <div class="">

      <NavBar></NavBar>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
</style>
