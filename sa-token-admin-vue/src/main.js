/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:46:10
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-29 09:59:30
 */
import request from '@/utils/request'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI, {
  size: 'small'
});

import '@/icons' // icon
import '@/config/permission' // permission control
import '@/styles/index.scss' // global css

import directive from './directive' // directive
Vue.use(directive)

import {handleTree} from '@/utils/formatter'


Vue.config.productionTip = false

// 全局方法挂载
Vue.prototype.$axios = request
Vue.prototype.handleTree = handleTree


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
