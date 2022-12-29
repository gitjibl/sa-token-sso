package com.example.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.SysMenu;
import com.example.system.domain.TreeSelect;
import com.example.system.domain.vo.RouterVo;


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
     *
     * @param menuId
     * @return
     */
    boolean hasChildByMenuId(Integer menuId);

    /**
     * 查询菜单是否存在角色
     *
     * @param menuId
     * @return
     */
    boolean checkMenuExistRole(Integer menuId);

    /**
     * 根据用户ID查询项目菜单树信息
     * @param userId
     * @param projectId
     * @return
     */
    List<SysMenu> selectMenuTreeByUserId(Integer userId,String projectId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus
     * @return
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);
}
