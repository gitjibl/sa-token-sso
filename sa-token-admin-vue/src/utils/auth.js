/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-09-26 10:24:29
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-13 13:30:18
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
