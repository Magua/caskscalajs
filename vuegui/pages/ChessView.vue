<template lang="jade">
<div>
  <div>
  <ul id="array-rendering">
    <li v-for="t in trainings">
      {{ t.id }}
    </li>
  </ul>
  </div>
  <div><button @click="fetchChess">Hämta</button></div>
  <div><button @click="createChess('move')">Skapa</button></div>
</div>
</template>

<script>
export default {
  data() {
    return {
      trainings: [],
    };
  },
  methods: {
    fetchChess: function () {
      this.$axios
        .get("/chess")
        .then((response) => (this.trainings = response.data.trainings));
    },
    createChess: function (move) {
      this.$axios
        .post("/chess", {
          move: move,
        });
    },
  },
};
</script>
