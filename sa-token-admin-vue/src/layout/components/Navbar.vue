<template>
  <div class="navbar">
    <hamburger
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown
        class="avatar-container"
        trigger="click"
        @command="handleCommand"
      >
        <div class="avatar-wrapper">
          <img :src="require('@assets/images/user.png')" class="user-avatar" />
          <span class="user-name"> {{ userInfo.nickname }} </span>
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item command="password">用户信息</el-dropdown-item>
          <el-dropdown-item command="password">修改密码</el-dropdown-item>
          <el-dropdown-item divided command="loginout"
            >退出登录</el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import { logOut } from "@/api/login";
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
      }
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