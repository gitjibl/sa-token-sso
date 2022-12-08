package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.domain.SysUser;

import java.util.List;

/**
* @author jibl
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-11-28 13:46:14
*/
public interface SysRoleService extends IService<SysRole> {

    IPage getPageList(SysRole sysRole);

    boolean updateRoleMenu(SysRole sysRole);

    /**
     * 获取已授权的用户
     * @param user
     * @return
     */
    IPage getAuthUsers(SysUser user);

    /**
     * 获取未授权的用户
     * @param user
     * @return
     */
    IPage getUnAuthUsers(SysUser user);

    /**
     * 角色授权用户
     * @param list
     * @param roleId
     */
    void authUser(List<Integer> list, Integer roleId);

    /**
     * 角色取消授权用户
     * @param list
     * @param roleId
     */
    void cancelAuthUser(List<Integer> list, Integer roleId);
}
