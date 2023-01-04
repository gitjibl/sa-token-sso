/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-27 17:00:04
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-27 17:00:26
 */

import hasPermi from './permission/hasPermi'

const install = function(Vue) {
  Vue.directive('hasPermi', hasPermi)
}

if (window.Vue) {
  window['hasPermi'] = hasPermi
  Vue.use(install); // eslint-disable-line
}

export default install
