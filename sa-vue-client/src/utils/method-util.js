/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-22 17:20:26
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-22 17:20:43
 */


// 从url中查询到指定名称的参数值
export const getParam = function(name, defaultValue){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == name){return pair[1];}
    }
    return(defaultValue == undefined ? null : defaultValue);
}

