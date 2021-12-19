<template lang="jade">
<div>
  <b-table striped hover :items="tableData" :fields=fields @row-clicked="myRowClickHandler" @primary-key=_id />

  <input v-model="name" placeholder="enter name" />
  <input v-model="moves" placeholder="enter moves" />
  <button @click="createChess()">Skapa ny</button>
</div>
</template>

<script>
export default {
  data() {
    return {
      trainings: [{ id: "ove", name: "d", moves: { ms: [] } }],
      moves: "e4",
      name: "",
      fields: ["name", "moves"],
    };
  },
  computed: {
    tableData: function () {
      return this.trainings.map((t) => {
        return {
          id: t.id,
          name: t.name,
          moves: t.moves.ms.join(", "),
        };
      });
    },
  },
  created() {
    this.fetchChess();
  },
  methods: {
    myRowClickHandler(record, index) {
      this.$router.push({
        path: "/edit/" + record.id,
      });
    },
    fetchChess: function () {
      this.$axios
        .get("/chess")
        .then((response) => (this.trainings = response.data.trainings));
    },
    createChess: function () {
      var that = this;
      this.$axios
        .post("/chess", {
          moves: that.moves,
          name: that.name,
        })
        .then((r) => {
          that.moves = "";
          that.name = "";
          that.fetchChess();
        });
    },
  },
};
</script>
