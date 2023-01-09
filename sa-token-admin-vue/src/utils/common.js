/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2023-01-05 15:35:15
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-05 16:05:52
 */
/**
* 参数处理
* @param {*} params  参数
*/
export function tansParams(params) {
    let result = ''
    for (const propName of Object.keys(params)) {
        const value = params[propName];
        var part = encodeURIComponent(propName) + "=";
        if (value !== null && value !== "" && typeof (value) !== "undefined") {
            if (typeof value === 'object') {
                for (const key of Object.keys(value)) {
                    if (value[key] !== null && value[key] !== "" && typeof (value[key]) !== 'undefined') {
                        let params = propName + '[' + key + ']';
                        var subPart = encodeURIComponent(params) + "=";
                        result += subPart + encodeURIComponent(value[key]) + "&";
                    }
                }
            } else {
                result += part + encodeURIComponent(value) + "&";
            }
        }
    }
    return result
}

// 验证是否为blob格式
export async function blobValidate(data) {
    try {
        const text = await data.text();
        JSON.parse(text);
        return false;
    } catch (error) {
        return true;
    }
}

export function formatJson(filterVal, jsonData) {
    return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
            return parseTime(v[j])
        } else {
            return v[j]
        }
    }))
}
