package com.example.admin.service;

import com.example.admin.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.domain.TreeSelect;

import java.util.List;

/**
* @author jibl
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2022-11-28 11:35:36
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据项目ID查询系统菜单列表
     *
     * @param sysMenu
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu sysMenu);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Integer> selectMenuListByRoleId(Integer roleId);

    /**
     * 是否存在菜单子节点
     * @param menuId
     * @return
     */
    boolean hasChildByMenuId(Integer menuId);

    /**
     * 查询菜单是否存在角色
     * @param menuId
     * @return
     */
    boolean checkMenuExistRole(Integer menuId);
}
