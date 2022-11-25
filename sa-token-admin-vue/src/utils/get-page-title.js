/*
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-07-27 14:22:23
 * @LastEditors: jibl
 * @LastEditTime: 2022-07-27 14:32:08
 */
import defaultSettings from '@/config/settings'

const title = defaultSettings.title || 'XXXXXXXXXXXXX系统'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
