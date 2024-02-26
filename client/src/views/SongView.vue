<template>
  <div>
    <h1 class="text-4xl my-3 ml-3">Song List</h1>
    <div class="flex flex-col justify-center mb-3">
      <input
        type="text"
        v-model="page.search"
        @change="searchSong"
        class="input input-bordered max-w-md mx-auto"
      />
    </div>
    <div class="flex flex-col justify-center gap-2 max-w-xl mx-auto">
      <Song v-for="song in songs" :song="song" />
    </div>
    <ErrorBadge v-if="page.error" :errorMessage="page.errorMessage" />
    <div class="join flex flex-row justify-center mt-3">
      <button class="join-item btn" @click="firstPage" :disabled="page.currentPage == 0">
        First Page
      </button>
      <button class="join-item btn" @click="prevPage" :disabled="page.currentPage == 0">«</button>
      <button class="join-item btn">Page {{ page.currentPage + 1 }} / {{ page.num }}</button>
      <button class="join-item btn" @click="nextPage" :disabled="page.currentPage == page.num - 1">
        »
      </button>
      <button class="join-item btn" @click="lastPage" :disabled="page.currentPage == page.num - 1">
        Last Page
      </button>
    </div>
  </div>
</template>

<script setup>
import Song from '@/components/Song.vue'
import ErrorBadge from '../components/ErrorBadge.vue'
import { ref, onMounted, reactive } from 'vue'
const page = reactive({
  error: false,
  errorMessage: '',
  loading: true,
  currentPage: 0,
  num: 0,
  search: ''
})
const songs = ref([])
function searchSong () {
  if (page.search != '') {
    fetchSearch()
    page.currentPage = 0
  } else {
    page.error = false
    fetchPage()
  }
}
async function fetchSearch () {
  try {
    const response = await fetch(
      'http://localhost:8080/api/songs/search/findByTitleContaining?title=' + page.search
    )
    songs.value = await response.json()
    try {
      if (songs.value.page.totalPages) {
        page.num = songs.value.page.totalPages
      } else {
        page.num = 0
      }
    } catch (error) {
      page.num = 0
    }
    if (songs.value.error) {
      page.error = true
      page.errorMessage = 'No songs can be found. Please adjust your search.'
      songs.value = []
      return
    }
    if (!songs.value._embedded) {
      songs.value = [songs.value]
    } else {
      songs.value = songs.value._embedded.songs
    }
    page.error = false
  } catch (error) {
    page.error = true
    page.errorMessage = 'No songs can be found. Please adjust your search.'
    songs.value = []
  }
}
function nextPage () {
  if (page.currentPage < page.num - 1) {
    page.currentPage++
  }
  fetchPage()
}
function prevPage () {
  if (page.currentPage > 0) {
    page.currentPage--
  }
  fetchPage()
}

function firstPage () {
  page.currentPage = 0
  fetchPage()
}

function lastPage () {
  page.currentPage = page.num - 1
  fetchPage()
}

async function fetchPage () {
  const response = await fetch('http://localhost:8080/api/songs?size=5&page=' + page.currentPage)
  songs.value = await response.json()
  songs.value = songs.value._embedded.songs
}

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/api/songs?size=5')
    songs.value = await response.json()
    page.num = songs.value.page.totalPages
    songs.value = songs.value._embedded.songs
    page.error = false
  } catch (error) {
    console.log(error)
    page.error = true
    page.errorMessage = 'Songs konnten nicht geladen werden'
  }
  page.loading = false
})
</script>
