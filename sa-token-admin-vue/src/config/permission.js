/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:19:29
 * @LastEditors: jibl
 * @LastEditTime: 2022-07-27 17:52:11
 */
import router from '@/router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/get-page-title'
import { asyncRoutes, constantRoutes } from '@/router'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login'] // 白名单

router.beforeEach(async(to, from, next) => {
  NProgress.start()

  document.title = getPageTitle(to.meta.title)

  next()
  
  NProgress.done()
})

router.afterEach(() => {
  NProgress.done()
})
