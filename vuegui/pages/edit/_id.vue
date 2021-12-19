<template lang="jade">
<div>
  <h1>Edit trainings</h1>
  <p>Name</p>
  <input v-model="training.name" placeholder="new name" />
  <p>Moves</p>
  <input v-model="training.moves" placeholder="new moves" />
  <button :disabled="updateNeeded" @click="update()">Update</button>
  <button @click="_delete()">Delete</button>
  
</div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      training: {
        savedName: "",
        name: "",
        savedMoves: "",
        moves: "",
      },
    };
  },
  created() {
    this.fetch();
  },
  computed: {
    updateNeeded: function () {
      return  this.training.savedName == this.training.name &&
        this.training.savedMoves == this.training.moves
    }
  },
  methods: {
    fetch: function () {
      this.id = this.$route.params.id;
      this.$axios.get("/chess/" + this.id).then(
        (response) =>
          (this.training = {
            name: response.data.name,
            savedName: response.data.name,
            moves: response.data.moves.ms.join(", "),
            savedMoves: response.data.moves.ms.join(", "),
          })
      );
    },
    _delete: function () {
      this.$axios.delete("/chess/" + this.id).then((_) => this.$router.go(-1));
    },
    update: function () {
      var that = this;
      this.$axios.post("/chess/" + this.id, {
        moves: that.training.moves,
        name: that.training.name,
      })
      .then((_) => that.fetch());
    },
  },
};
</script>
