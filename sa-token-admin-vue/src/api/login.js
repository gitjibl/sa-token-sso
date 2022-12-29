/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-13 13:29:31
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-29 13:53:43
 */
import request from '@/utils/request'
import store from '@/store'

export const LoginUrl = process.env.VUE_APP_SSO_LOGIN_URL + process.env.VUE_APP_ADMIN_URL

export const LogOutUrl = process.env.VUE_APP_SSO_LOGOUT_URL + process.env.VUE_APP_ADMIN_URL

// 获取用户详细信息
export function getLoginUser() {
    return request({
      url: '/getLoginUser',
      method: 'get',
      params:{
        projectId:"CS001"
      }
    })
  }

export function getRouters(){
  return request({
    url: '/getRouters',
    method: 'get'
  })
}

export function logOut(){
  store.dispatch("user/logOut")
  location.href = LogOutUrl
}

