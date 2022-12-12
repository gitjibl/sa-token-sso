package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jibl
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2022-11-28 11:35:12
*/
public interface SysUserService extends IService<SysUser> {

    IPage getPageList(SysUser sysUser);

    boolean updateUser(SysUser sysUser);

    boolean insertUser(SysUser sysUser);

    boolean deleteUserBatch(List<Integer> userIds);

    /**
     * 更新用户角色关联
     *
     * @param sysUser
     * @return
     */
    boolean updateUserRole(SysUser sysUser);
}
