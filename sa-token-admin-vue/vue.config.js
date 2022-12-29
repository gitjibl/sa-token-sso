/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 17:01:02
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-29 13:58:42
 */
const path = require("path");
const webpack = require("webpack");
process.env.VUE_APP_TITLE = '统一用户管理系统';
module.exports = {
    publicPath: "./",
    //放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录
    assetsDir: "static",
    //如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
    productionSourceMap: false,
    configureWebpack: {
        resolve: {
            alias: {
                "@assets": path.join(__dirname, "src/assets"),
                "@components": path.join(__dirname, "src/components"),
                "@views": path.join(__dirname, "src/views"),
                "@utils": path.join(__dirname, "src/utils"),
            },
        },
        //支持jquery
        plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "windows.jQuery": "jquery"
            })
        ]
    },
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
    chainWebpack: config => {
        // svg是个基础loader
        const svgRule = config.module.rule('svg')
        // 清除已有的所有 loader。
        svgRule.uses.clear()
        // 添加要替换的 loader
        svgRule
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            })
    },
    pages: {
        index: {
            entry: 'src/main.js',
            title: process.env.VUE_APP_TITLE,
        },
    },
}