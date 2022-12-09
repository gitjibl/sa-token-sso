/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-09-26 10:24:29
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-22 17:33:27
 */
import Cookies from 'js-cookie'
import request from '@/utils/request';

const TokenKey = 'satoken'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
// 获取用户详细信息
export function getLoginUserInfo() {
  return request({
    url: '/user/getLoginUserInfo',
    method: 'get'
  })
}