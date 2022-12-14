/*
 * @Descripttion: 用户信息缓存
 * @Author: jibl
 * @Date: 2022-12-13 10:38:46
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-13 13:33:13
 */

const state = {
  userInfo:{},
}

const mutations = {
  SET_USERINFO: (state, userInfo) => {
    state.userInfo = userInfo
  }
}

const actions = {
  setUserInfo({ commit }, userInfo) {
    commit('SET_USERINFO',userInfo)
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
