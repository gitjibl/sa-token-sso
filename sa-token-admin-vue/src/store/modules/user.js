/*
 * @Descripttion: 用户信息缓存
 * @Author: jibl
 * @Date: 2022-12-13 10:38:46
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-27 17:25:27
 */
import { getToken, removeToken } from '@/utils/auth'
const state = {
  token: getToken(),
  userInfo: {},
  permissions: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USERINFO: (state, userInfo) => {
    state.userInfo = userInfo
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  }
}

const actions = {

  setUserInfo({ commit }, userInfo) {
    commit('SET_USERINFO', userInfo)
  },
  setPermissions({ commit }, permissions) {
    commit('SET_PERMISSIONS', permissions)
  },
  logOut({ commit, state }) {
    commit('SET_TOKEN', '')
    commit('SET_USERINFO', {})
    commit('SET_PERMISSIONS', [])
    removeToken()
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
