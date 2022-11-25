/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:46:10
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-22 17:30:10
 */
import request from '@/utils/request'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import '@/icons' // icon
import '@/config/permission' // permission control

import '@/styles/index.scss' // global css

Vue.use(ElementUI, {
  size: 'mini'
});
Vue.config.productionTip = false
Vue.prototype.$axios = request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
