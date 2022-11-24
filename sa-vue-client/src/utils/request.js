/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-09-22 13:43:06
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-22 17:41:28
 */
// import Cookies from 'js-cookie'
import axios from 'axios'
import store from '@/store'
import {
    getToken
} from '@/utils/auth'
import {
    Notification,
    Message,
    MessageBox,
    Loading
} from 'element-ui'
import errorCode from '@/utils/errorCode'


axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: '/baseApi',
    // 超时
    timeout: 30000
})

// request拦截器
service.interceptors.request.use(config => {
    if (getToken()) {
        config.headers['satoken'] =  getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
        // 未设置状态码则默认成功状态
        const code = res.data.code || 200;
        // 获取错误信息
        const msg = errorCode[code] || res.data.msg || errorCode['default']
        // 二进制数据则直接返回
        if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
            return res.data
        }
        if (code === 401) {
            MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
                confirmButtonText: '重新登录',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                location.href = 'http://localhost:9000/sso/auth?redirect='+location.href
            }).catch(() => {

            });
            return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
        } else if (code === 200) {
            return Promise.resolve(res.data)
        } else {
            Notification({
                title: '错误',
                message: msg,
                type: 'error'
            });
            return Promise.reject(new Error(msg))
        }
    },
    error => {
        Notification({
            title: '错误',
            message: '操作失败',
            type: 'error'
        });
        return Promise.reject(error)
    }
)


export default service