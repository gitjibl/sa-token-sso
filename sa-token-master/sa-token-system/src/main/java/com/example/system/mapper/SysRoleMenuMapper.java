package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.domain.SysRoleMenu;


import java.util.List;

/**
* @author jibl
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
* @createDate 2022-12-06 16:47:01
* @Entity com.example.admin.domain.SysRoleMenu
*/
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量删除角色菜单关联信息
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    void deleteRoleMenuBatch(List<Integer> roleIds);

    /**
     * 批量新增角色菜单关联
     * @param sysRoleMenus
     */
    void insertRoleMenuBatch(List<SysRoleMenu> sysRoleMenus);
}




