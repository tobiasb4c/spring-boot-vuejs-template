<template>
  <div>
    <div class="flex flex-row justify-center md:justify-between my-3 mx-6">
      <h1 class="text-4xl">Song List</h1>
      <button class="btn btn-primary" onclick="add.showModal()">Add Song</button>
    </div>

    <!--Edit Song Modal-->
    <dialog id="edit" class="modal">
    <div class="modal-box">
      <h3 class="font-bold text-lg">Edit the Song!</h3>
      <div class="modal-action">
        <form method="dialog" class="flex flex-col w-full gap-2">
          <div class="flex flex-col justify-center mb-8">
            <div class="flex flex-row justify-between w-full">
              <div>
                <label for="title" class="label">Title</label>
                <input
                  v-model.text="useSongStore().editSong.title"
                  type="text"
                  id="title"
                  class="input input-bordered"
                />
              </div>
              <div>
                <label for="artist" class="label">Artist</label>
                <input
                  v-model.text="useSongStore().editSong.artist"
                  type="text"
                  id="artist"
                  class="input input-bordered"
                />
              </div>
            </div>
            <div class="flex flex-row justify-between w-full">
              <div>
                <label for="genre" class="label">Genre</label>
                <input
                  v-model.text="useSongStore().editSong.genre"
                  type="text"
                  id="genre"
                  class="input input-bordered"
                />
              </div>
              <div>
                <label for="duration" class="label">Duration</label>
                <input
                  v-model.number="useSongStore().editSong.duration"
                  type="text"
                  id="duration"
                  class="input input-bordered"
                />
              </div>
            </div>
          </div>
          <div class="flex flex-row justify-evenly w-full">
            <button class="btn btn-secondary">Close</button>
            <button class="btn btn-primary" @click="updateSong(useSongStore().editSong)">Update Song</button>
          </div>
        </form>
      </div>
    </div>
  </dialog>

    <!--Add Song Modal-->
    <dialog id="add" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add a new Song!</h3>
        <div class="modal-action">
          <form method="dialog" class="flex flex-col w-full gap-2">
            <div class="flex flex-col justify-center mb-8">
              <div class="flex flex-row justify-between w-full">
                <div>
                  <label for="title" class="label">Title</label>
                  <input
                    v-model.text="newSong.title"
                    type="text"
                    id="title"
                    class="input input-bordered"
                  />
                </div>
                <div>
                  <label for="artist" class="label">Artist</label>
                  <input
                    v-model.text="newSong.artist"
                    type="text"
                    id="artist"
                    class="input input-bordered"
                  />
                </div>
              </div>
              <div class="flex flex-row justify-between w-full">
                <div>
                  <label for="genre" class="label">Genre</label>
                  <input
                    v-model.text="newSong.genre"
                    type="text"
                    id="genre"
                    class="input input-bordered"
                  />
                </div>
                <div>
                  <label for="duration" class="label">Duration</label>
                  <input
                    v-model.number="newSong.duration"
                    type="text"
                    id="duration"
                    class="input input-bordered"
                  />
                </div>
              </div>
            </div>
            <div class="flex flex-row justify-evenly w-full">
              <button class="btn btn-secondary">Close</button>
              <button class="btn btn-primary" @click="addSong">Add Song</button>
            </div>
          </form>
        </div>
      </div>
    </dialog>

    <!--Searchbar for Songs-->
    <div class="flex flex-col justify-center mb-3">
      <input
        type="text"
        v-model="page.search"
        @change="searchSong"
        class="input input-bordered max-w-md mx-auto"
      />
    </div>
    <!--Songs-->
    <div class="flex flex-col justify-center gap-2 max-w-xl mx-auto">
      <Song v-for="(song, index) in songs" :song="song" :id="index"/>
    </div>
    <ErrorBadge v-if="page.error" :errorMessage="page.errorMessage" />

    <!--Pagiation Buttons-->
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
import { useToast } from 'vue-toastification'
import { useSongStore } from '../stores/songStore'

const toast = useToast()

const page = reactive({
  error: false,
  errorMessage: '',
  loading: true,
  currentPage: 0,
  num: 0,
  search: ''
})

const songs = ref([])

const newSong = ref({
  title: '',
  artist: '',
  genre: '',
  duration: ''
})

async function updateSong(song) {
  try {
    // Update song
    const updatedSong = await fetch(song.link, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(song)
    }).then(() => {
      fetchPage()
      toast.success('Song updated successfully!')
    })

  } catch (e) {
    console.log(e)
    toast.error('Song could not be updated!')
  }
}

function addSong () {
  try {
    console.log('Add')
    console.log(newSong.value)
    // Create new song
    const addedSong = fetch('http://localhost:8080/api/songs', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newSong.value)
    }).then(() => {
      fetchPage()
      toast.success('Song added successfully!')
    })
    // clear form
    newSong.value = {
      title: '',
      artist: '',
      genre: '',
      duration: ''
    }
  } catch (e) {
    toast.error('Song could not be added!')
  }
}
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
    /*
    page.error = true
    page.errorMessage = 'No songs can be found. Please adjust your search.'
    */
    toast.error('No songs can be found. Please adjust your search.')
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
  try {
    const response = await fetch('http://localhost:8080/api/songs?size=5&page=' + page.currentPage)
    songs.value = await response.json()
    songs.value = songs.value._embedded.songs
    toast.success('Songs loaded successfully!')
  } catch (error) {
    toast.error('Songs konnten nicht geladen werden')
  }
}

onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/api/songs?size=5')
    songs.value = await response.json()
    page.num = songs.value.page.totalPages
    songs.value = songs.value._embedded.songs
    page.error = false
  } catch (error) {
    toast.error('Songs konnten nicht geladen werden')
  }
  page.loading = false
})
</script>
