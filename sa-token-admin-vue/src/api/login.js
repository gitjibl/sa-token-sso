/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-13 13:29:31
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-13 17:44:56
 */
import request from '@/utils/request'

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

export const LoginUrl = process.env.VUE_APP_SSO_LOGIN_URL + location.href

export const LogOutUrl = process.env.VUE_APP_SSO_LOGOUT_URL + location.href