<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:48:00
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-09 13:32:27
-->
<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ "555555555" }}</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
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
}
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
