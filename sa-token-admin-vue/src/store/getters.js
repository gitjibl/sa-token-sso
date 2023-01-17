/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:37:12
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-17 11:07:38
 */
const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  permission_routes: state => state.permission.routes,
  token: state => state.user.token,
  userInfo: state => state.user.userInfo,
  permissions: state => state.user.permissions,
}
export default getters
