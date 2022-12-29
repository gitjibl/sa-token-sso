/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-28 09:38:28
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-29 11:53:44
 */
/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

/* Layout */
import Layout from '@/layout'
/* ParentView */
import ParentView from '@/components/ParentView'

export const constantRoutes = [{
  path: '/redirect',
  component: Layout,
  hidden: true,
  children: [{
    path: '/redirect/:path(.*)',
    component: () => import('@/views/redirect/index')
  }]
},
{
  path: '/',
  component: Layout,
  redirect: '/dashboard',
  children: [{
    path: 'dashboard',
    name: 'Dashboard',
    component: () => import('@/views/dashboard/index'),
    meta: {
      title: '首页',
      icon: 'dashboard',
      affix: true
    }
  }]
},
// 提示页面 401 404
{
  path: '/error',
  component: ParentView,
  hidden: true,
  name: 'Error',
  children: [{
    path: '401',
    name: 'Page401',
    component: () => import('@/views/error/401'),
  },
  {
    path: '404',
    name: 'Page404',
    component: () => import('@/views/error/404'),
  }
  ],
},
];
export const asyncRoutes = [
  {
    path: '/system',
    component: Layout,
    redirect: 'noRedirect',
    meta: {
      title: '系统管理',
      icon: 'example'
    },
    children: [{
      path: 'project',
      name: 'Project',
      component: () => import('@/views/system/project'),
      meta: {
        title: '项目管理',
      }
    }, {
      path: 'user',
      name: 'User',
      component: () => import('@/views/system/user'),
      meta: {
        title: '用户管理',
      }
    }, {
      path: 'role',
      name: 'Role',
      component: () => import('@/views/system/role'),
      meta: {
        title: '角色管理',
      }
    }, {
      path: 'menu',
      name: 'Menu',
      component: () => import('@/views/system/menu'),
      meta: {
        title: '菜单管理',
      }
    }, {
      path: 'dept',
      name: 'Dept',
      component: () => import('@/views/system/dept/index'),  //配置部门管理组件地址
      meta: {
        title: '部门管理',
      }
    },]
  }
]
const createRouter = () => new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
// export function resetRouter() {
//   const newRouter = createRouter()
//   router.matcher = newRouter.matcher // reset router
// }

export default router