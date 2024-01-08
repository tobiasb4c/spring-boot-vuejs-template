<script setup>
import { RouterLink} from 'vue-router'
import ThemeToggle from './ThemeToggle.vue'
</script>
<template>
  <div class="navbar bg-primary-content rounded-2xl w-[100%] my-1">
    <div class="flex-1">
      <a class="btn btn-ghost text-xl">SEW</a>
    </div>
    <div class="flex-none">
      <ul class="menu menu-horizontal items-center px-1 text-lg">
        <li><RouterLink to="/">Home</RouterLink></li>
        <li><RouterLink to="/songs">Songs</RouterLink></li>
        <li>
          <ThemeToggle></ThemeToggle>
        </li>
      </ul>
    </div>
  </div>
</template>
