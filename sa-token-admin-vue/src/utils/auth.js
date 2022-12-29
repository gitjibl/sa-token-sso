/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-09-26 10:24:29
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-27 17:26:09
 */
import Cookies from 'js-cookie'

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
