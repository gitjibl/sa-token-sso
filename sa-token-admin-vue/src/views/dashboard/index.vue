<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:48:00
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-13 16:21:06
-->
<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ "555555555" }}</div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { LogOutUrl } from "@/api/login";
export default {
  name: "Dashboard",
  computed: {
    ...mapGetters(["name"]),
  },
  mounted() {},
  methods: {
    logOut() {
      // location.href = 'http://localhost:9000/sso/signout?'+'&back='+'http://localhost:9000/sso/auth?redirect='+location.href;
      location.href = LogOutUrl;
    },
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
