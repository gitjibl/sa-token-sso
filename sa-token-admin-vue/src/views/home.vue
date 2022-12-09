<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:56:45
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-09 13:34:04
-->
<!--  -->
<template>
  <div style="text-align: center">
    <h1>home</h1>
    <button @click="logOut">退出</button>
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
        url: "/user/getLoginUserInfo",
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
    logOut(){
      location.href = 'http://localhost:9000/sso/signout?'+'&back='+'http://localhost:9000/sso/auth?redirect='+location.href;
    }
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>