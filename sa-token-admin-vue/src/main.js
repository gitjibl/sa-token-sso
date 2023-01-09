/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 16:46:10
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-09 09:35:48
 */
import request from '@/utils/request'
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { download } from '@/utils/request'
import * as common  from '@/utils/common'
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
Vue.prototype.$projectId = 'CS001'
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
