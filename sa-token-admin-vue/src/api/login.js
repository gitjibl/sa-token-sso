/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-13 13:29:31
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-17 10:06:09
 */
import request from '@/utils/request'
import store from '@/store'
import { projectId } from '@/config/settings'

export const LoginUrl = process.env.VUE_APP_SSO_LOGIN_URL + process.env.VUE_APP_ADMIN_URL

export const LogOutUrl = process.env.VUE_APP_SSO_LOGOUT_URL + process.env.VUE_APP_ADMIN_URL

// 获取用户详细信息
export function getLoginUser() {
  return request({
    url: '/getLoginUser',
    method: 'get',
    params: {
      projectId: projectId
    }
  })
}

export function getRouters() {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}

export function logOut() {
  store.dispatch("user/logOut")
  location.href = LogOutUrl
}

