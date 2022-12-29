<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:48:00
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-29 14:33:15
-->
<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ "555555555" }}</div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {},
  methods: {

  },
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
