<template>
  <!--Edit Modal-->

  <!--Song Card-->
  <div class="collapse collapse-arrow bg-base-200">
    <input type="radio" name="my-accordion-2" checked="checked" />
    <div class="collapse-title text-xl font-medium">{{ song.title }} from {{ song.artist }}</div>
    <div class="collapse-content flex flex-row justify-start text-lg gap-4">
      <p>Genre: {{ song.genre }}</p>
      <p>Duration: {{ secondsToMinutes(song.duration) }}</p>
      <button class="btn btn-primary" onclick="edit.showModal()" @click="editSongToStore">
        Edit Song
      </button>

      <!--Modal-->
      <!--Edit Song Modal-->
    </div>
  </div>
</template>

<script setup>
import { useSongStore } from '../stores/songStore'
import { ref } from 'vue'
import { useToast } from 'vue-toastification'

const toast = useToast()

const props = defineProps({
  song: Object,
  id: Number
})

const toEditSong = ref({
  id: props.id,
  title: props.song.title,
  artist: props.song.artist,
  genre: props.song.genre,
  duration: props.song.duration
})

function editSongToStore () {
  useSongStore().setEditSong(toEditSong.value)
}

function secondsToMinutes (seconds) {
  return Math.floor(seconds / 60) + ':' + ('0' + Math.floor(seconds % 60)).slice(-2)
}
</script>
