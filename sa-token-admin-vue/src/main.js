/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:46:10
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-17 15:06:35
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import request from '@/utils/request'
import Cookies from 'js-cookie'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { download } from '@/utils/request'
import * as common  from '@/utils/common'
Vue.use(ElementUI, {
  size: Cookies.get('size') || 'medium' 
});

import '@/icons' // icon
import '@/config/permission' // permission control
import '@/styles/index.scss' // global css
import settings from '@/config/settings'

import directive from './directive' // directive
Vue.use(directive)

import {handleTree} from '@/utils/formatter'




Vue.config.productionTip = false

// 全局属性、方法挂载
Vue.prototype.$axios = request
Vue.prototype.handleTree = handleTree
Vue.prototype.$settings = settings
Vue.prototype.download = download
Vue.prototype.$common = common

import UploadManage from '@components/Upload/UploadManage'
// 全局组件挂载
Vue.component('UploadManage', UploadManage)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
