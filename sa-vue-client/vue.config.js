/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 17:01:02
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-22 17:33:01
 */
module.exports = {
    devServer: {
        //被认为是索引文件的文件名
        index: "index.html",
        port: 9004,
        //dev-server在服务器启动后打开浏览器
        open: true,
        // 跨域代理
        proxy: {
            //配置跨域
            "/baseApi": {
                target: "http://localhost:9003",
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    "^/baseApi": "",
                },
            },
        },
    },
}