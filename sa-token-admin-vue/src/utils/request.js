/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-09-22 13:43:06
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-05 15:44:58
 */
// import Cookies from 'js-cookie'
import axios from 'axios'
import {
    getToken, removeToken
} from '@/utils/auth'
import {
    Notification,
    Message,
    MessageBox,
    Loading
} from 'element-ui'
import errorCode from '@/utils/errorCode'
import { LoginUrl, logOut } from '@/api/login'
import { tansParams, blobValidate } from "@/utils/common";
import { saveAs } from 'file-saver'
let downloadLoading;
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
        config.headers['satoken'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
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
        // MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
        //     confirmButtonText: '重新登录',
        //     cancelButtonText: '取消',
        //     type: 'warning'
        // }).then(() => {
        //     location.href = 'http://localhost:9000/sso/auth?redirect='+location.href
        // }).catch(() => {

        // });
        // logOut()
        removeToken()
        location.href = LoginUrl
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

// 通用下载方法
export function download(url, params, filename) {
    downloadLoading = Loading.service({ text: "正在下载数据，请稍候", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
    return service.post(url, params, {
        transformRequest: [(params) => { return tansParams(params) }],
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        responseType: 'blob'
    }).then(async (data) => {
        const isLogin = await blobValidate(data);
        if (isLogin) {
            const blob = new Blob([data])
            saveAs(blob, filename)
        } else {
            const resText = await data.text();
            const rspObj = JSON.parse(resText);
            const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
            Message.error(errMsg);
        }
        downloadLoading.close();
    }).catch((r) => {
        console.error(r)
        Message.error('下载文件出现错误，请联系管理员！')
        downloadLoading.close();
    })
}
export default service