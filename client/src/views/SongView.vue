<template>
  <div>
    <h1 class="text-4xl my-3 ml-3">Song List</h1>
    <div class="flex flex-col justify-center gap-2 max-w-xl mx-auto">
      <Song v-for="song in songs" :song="song" />
    </div>
    <ErrorBadge v-if="page.error" :message="page.errorMessage" />
  </div>
</template>

<script setup>
import Song from '@/components/Song.vue'
import ErrorBadge from '../components/ErrorBadge.vue'
import { ref, onMounted, reactive } from 'vue'
const page = reactive({
  error: false,
  errorMessage: '',
  loading: true
})
const songs = ref([])

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/api/songs')
    songs.value = await response.json()
    songs.value = songs.value._embedded.songs
    page.error = false
  } catch (error) {
    page.error = true
    page.errorMessage = 'Songs konnten nicht geladen werden'
  }
  page.loading = false
})
</script>
