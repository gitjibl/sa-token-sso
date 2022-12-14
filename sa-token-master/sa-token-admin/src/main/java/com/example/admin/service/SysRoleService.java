package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.domain.SysUser;
import com.example.admin.domain.TreeSelect;

import java.util.List;

/**
 * @author jibl
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service
 * @createDate 2022-11-28 13:46:14
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     *
     * @param sysRole
     * @return
     */
    IPage getPageList(SysRole sysRole);


    /**
     * 更新角色菜单关联
     *
     * @param sysRole
     * @return
     */
    boolean updateRoleMenu(SysRole sysRole);

    /**
     * 获取已授权的用户
     *
     * @param user
     * @return
     */
    IPage getAuthUsers(SysUser user);

    /**
     * 获取未授权的用户
     *
     * @param user
     * @return
     */
    IPage getUnAuthUsers(SysUser user);

    /**
     * 角色授权用户
     *
     */
    void authUser(SysRole sysRole);

    /**
     * 角色取消授权用户
     *
     * @param list
     * @param roleId
     */
    void cancelAuthUser(List<Integer> list, Integer roleId);

    /**
     * 获取全部角色集合
     *
     * @return
     */
    List<TreeSelect> roleTreeSelect();

    /**
     * 通过UserId获取角色集合
     *
     * @param userId
     * @return
     */
    List<Integer> selectRoleListByUserId(Integer userId);

    /**
     *
     * @param roleIds
     * @return
     */
    boolean deleteRoleBatch(List<Integer> roleIds);
}
