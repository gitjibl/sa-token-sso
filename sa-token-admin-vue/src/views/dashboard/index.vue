<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:48:00
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-03 17:03:15
-->
<template>
  <div class="dashboard-container">
    <div class="dashboard-text"></div>
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
