<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click" size="small" @command="handleCommand">
        <div class="avatar-wrapper">
          <img :src="require('@assets/images/user.png')" class="user-avatar" />
          <span class="user-name"> {{ userInfo.nickname }} </span>
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item command="password">修改密码</el-dropdown-item>
          <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
  import {
    mapGetters
  } from "vuex";
  import Breadcrumb from "@/components/Breadcrumb";
  import Hamburger from "@/components/Hamburger";
  import {
    logOut
  } from "@/api/login";
  export default {
    components: {
      Breadcrumb,
      Hamburger,
    },
    computed: {
      ...mapGetters(["sidebar", "userInfo"]),
    },
    methods: {
      toggleSideBar() {
        this.$store.dispatch("app/toggleSideBar");
      },
      handleCommand(command) {
        if (command == "loginout") {
          logOut();
        } else if (command == "password") {
          this.handleResetPwd()
        }
      },
      /** 重置密码按钮操作 */
      handleResetPwd() {
        this.$prompt('请输入"' + this.userInfo.username + '"的新密码', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnClickModal: false,
            inputPattern: /^.{5,20}$/,
            inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
          })
          .then(({
            value
          }) => {
            this.$axios({
              method: "get",
              url: "/user/resetUserPwd",
              params: {
                userId: this.userInfo.userId,
                password: value,
              },
            }).then((res) => {
              this.$notify.success({
                title: "成功",
                message: "操作成功",
              });
            });
          })
          .catch(() => {});
      },
    },
  };
</script>

<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background 0.3s;
      -webkit-tap-highlight-color: transparent;

      &:hover {
        background: rgba(0, 0, 0, 0.025);
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      .avatar-container {
        margin-right: 40px;

        .avatar-wrapper {
          display: flex;
          flex-direction: row;
          margin-top: 5px;
          position: relative;

          .user-name {
            line-height: 45px;
            // font-size: 15px;
            margin: 0px 0px 0px 8px;
          }

          .user-avatar {
            display: flex;
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 20px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 16px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>