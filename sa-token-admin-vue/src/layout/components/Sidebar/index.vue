<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 17:52:43
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-13 16:04:59
-->
<template>
  <div :class="{'has-logo':showLogo}">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu :default-active="activeMenu" :collapse="isCollapse" :background-color="variables.menuBg"
        :text-color="variables.menuText" :unique-opened="false" :active-text-color="variables.menuActiveText"
        :collapse-transition="false" mode="vertical">
        <sidebar-item v-for="route in routes" :key="route.path" :item="route" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
  import {
    mapGetters
  } from 'vuex'
  import Logo from './Logo'
  import SidebarItem from './SidebarItem'
  import variables from '@/styles/variables.scss'

  export default {
    components: {
      SidebarItem,
      Logo
    },
    computed: {
      ...mapGetters([
        'sidebar'
      ]),
      routes() {
        // return this.$router.options.routes
        return this.$store.state.permission.routes;
      },
      activeMenu() {
        debugger
        const route = this.$route
        const {
          meta,
          path
        } = route
        // if set path, the sidebar will highlight the path you set
        if (meta.activeMenu) {
          return meta.activeMenu
        }
        return path
      },
      showLogo() {
        return this.$store.state.settings.sidebarLogo
      },
      variables() {
        return variables
      },
      isCollapse() {
        return !this.sidebar.opened
      }
    }
  }

</script>
