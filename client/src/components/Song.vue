<template>
    <div class="collapse collapse-arrow bg-base-200">
        <input type="radio" name="my-accordion-2" checked="checked" />
        <div class="collapse-title text-xl font-medium">{{ song.title }} from {{ song.artist }}</div>
        <div class="collapse-content flex flex-row justify-start text-lg gap-4">
            <p>Genre: {{ song.genre }}</p>
            <p>Duration: {{ secondsToMinutes(song.duration) }}</p>
        </div>
    </div>
</template>

<script setup>
function secondsToMinutes(seconds) {
    return Math.floor(seconds / 60) + ':' + ('0' + Math.floor(seconds % 60)).slice(-2)
}
const props = defineProps({
    song: Object
})
</script>