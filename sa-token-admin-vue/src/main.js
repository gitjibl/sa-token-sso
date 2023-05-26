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

// 引入 activiti
// bpmn 工作流绘图工具的样式
import "bpmn-js/dist/assets/diagram-js.css"  // 左边工具栏以及编辑节点的样式
import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css"
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css"
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css"
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css' // 右边工具栏样式
// 引入 activiti


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
