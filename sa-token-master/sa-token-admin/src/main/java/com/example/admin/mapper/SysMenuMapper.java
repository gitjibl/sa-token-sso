package com.example.admin.mapper;

import com.example.admin.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jibl
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
 * @createDate 2022-11-28 11:35:36
 * @Entity com.example.admin.domain.SysMenu
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户所有权限
     *
     * @return 权限列表
     */
    List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByUserId(SysMenu menu);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Integer> selectMenuListByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据项目ID查询系统菜单列表
     *
     * @param projectId 项目ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuListByProjectId(@Param("projectId")String projectId);
}




