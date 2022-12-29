/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:37:12
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-27 17:30:51
 */
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  permission_routes: state => state.permission.routes,
  token: state => state.user.token,
  userInfo: state => state.user.userInfo,
  permissions: state => state.user.permissions,
}
export default getters
