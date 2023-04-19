/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:19:29
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-03 17:05:20
 */
import router from '@/router'
import store from '@/store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/get-page-title'
import {
  getToken
} from '@/utils/auth'
import {
  getLoginUser,
  LoginUrl
} from '@/api/login'

// import { Message, MessageBox, Notification, Loading } from 'element-ui'
NProgress.configure({
  showSpinner: false
})

const whiteList = ['/login'] // 白名单

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  document.title = getPageTitle(to.meta.title)



  if (getToken()) {
    /* has token*/
    if (!store.getters.userInfo.hasOwnProperty('username')) {


      /** 
       *  router.addRoutes 看着失效,但是没有
       *  el-menu 监听的是vuex router 
       *  
       */

      getLoginUser().then(res => {
        console.log("用户信息", res)
        if (res.status == 1) {
          router.replace({ name: 'Page401', params: { info: '', headline: '项目已停用！' } })
        }
        else if (res.user && res.user.status == 1){
          router.replace({ name: 'Page401', params: { info: '', headline: '用户被禁用,请联系管理员！' } })
        }
        // else if (res.user.roleKeys.length == 0) {
        //   router.replace({ name: 'Page401', params: { info: '当前帐号没有操作权限,请联系管理员。', headline: '您没有操作权限...' } })
        // }

        //存储用户信息
        store.dispatch('user/setUserInfo', res.user);
        store.dispatch('user/setPermissions', res.user.perms);
        //生成路由表
        store.dispatch('permission/generateRoutes').then(accessRoutes => {
          //动态添加可访问路由表
          accessRoutes.forEach(res => {
            router.addRoute(res);
          })
          next({
            ...to,
            replace: true
          })
        })

      })
    } else {
      next()
    }


  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      location.href = LoginUrl
      NProgress.done()
    }
  }

})

router.afterEach(() => {
  NProgress.done()
})