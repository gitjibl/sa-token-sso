<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 17:52:42
 * @LastEditors: jibl
 * @LastEditTime: 2022-09-08 15:46:58
-->
<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </section>
</template>

<script>
  export default {
    name: "AppMain",
    computed: {
      cachedViews() {
        return this.$store.state.tagsView.cachedViews;
      },
      key() {
        return this.$route.path;
      }
    }
  };
</script>

<style scoped>
  .app-main {
    /*50 = navbar  */
    height: calc(100vh - 94px);
    /* height: 100%; */
    width: 100%;
    box-sizing: border-box;
    overflow-y: auto;
  }

  .fixed-header+.app-main {
    margin-top: 90px
  }
</style>

<style lang="scss">
  // fix css style bug in open el-dialog
  .el-popup-parent--hidden {
    .fixed-header {
      padding-right: 15px;
    }
  }
</style>