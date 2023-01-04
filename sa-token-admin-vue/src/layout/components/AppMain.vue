<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 17:52:42
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-04 09:20:31
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
    },
  },
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

.fixed-header + .app-main {
  margin-top: 90px;
}
</style>

<style lang="scss">
/* fade-transform */
// .fade-transform-leave-active,
// .fade-transform-enter-active {
//   transition: all 0.2s;
// }

// .fade-transform-enter {
//   opacity: 0;
//   transform: translateX(0px);
// }

// .fade-transform-leave-to {
//   opacity: 0;
//   transform: translateX(0px);
// }
/* fade-transform */
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all 0.5s;
}

.fade-transform-enter {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>