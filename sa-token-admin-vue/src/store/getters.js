/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:37:12
 * @LastEditors: jibl
 * @LastEditTime: 2022-07-28 08:58:41
 */
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  permission_routes: state => state.permission.routes,
}
export default getters
