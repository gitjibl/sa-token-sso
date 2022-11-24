<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:56:45
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-22 17:33:59
-->
<!--  -->
<template>
  <div style="text-align: center">
    <h1>home</h1>
  </div>
</template>

<script>
import { Notification } from "element-ui";
export default {
  data() {
    return {};
  },
  created() {},
  mounted() {
    this.getToken();
  },
  methods: {
    getToken() {
      this.$axios({
        url: "/getToken",
        method: "get",
        params: {},
      }).then((res) => {
        Notification({
          title: "成功",
          message: "访问成功",
          type: "success",
        });
      });
    },
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>