/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:19:29
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-26 17:46:40
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
        if (res.roles.length == 0) {
          this.$notify({
            title: '警告',
            message: '当前用户没有分配角色！请先分配角色后重新登入！',
            type: 'warning'
          });
          return
        }

        //存储用户信息
        store.dispatch('user/setUserInfo', res);
        //生成路由表
        store.dispatch('permission/generateRoutes', ['admin']).then(accessRoutes => {
          console.log("动态路由", accessRoutes)
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
      // location.href = "http://localhost:9000/sso/auth?redirect=http://localhost:9003/sso/login?back=http://localhost:9004/"
      location.href = LoginUrl
      NProgress.done()
    }
  }

})

router.afterEach(() => {
  NProgress.done()
})