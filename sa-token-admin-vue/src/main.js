/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:46:10
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-12 17:38:42
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

import {handleTree} from '@/utils/formatter'

Vue.use(ElementUI, {
  size: 'small'
});
Vue.config.productionTip = false

// 全局方法挂载
Vue.prototype.$axios = request
Vue.prototype.handleTree = handleTree


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
