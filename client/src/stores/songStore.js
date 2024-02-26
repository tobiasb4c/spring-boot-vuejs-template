import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useSongStore = defineStore('songsStore', () => {
  const editSong = ref({
    id: '',
    title: '',
    artist: '',
    genre: '',
    duration: ''
  })

  // Set editSong
  function setEditSong (song) {
    editSong.value = song
    console.log('editSong', editSong.value)
  }

  return { editSong, setEditSong }
})
