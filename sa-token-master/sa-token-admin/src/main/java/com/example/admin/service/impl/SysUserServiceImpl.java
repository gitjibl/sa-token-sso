package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysUser;
import com.example.admin.domain.SysUserRole;
import com.example.admin.mapper.SysUserRoleMapper;
import com.example.admin.service.SysUserService;
import com.example.admin.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jibl
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2022-11-28 11:35:12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage getPageList(SysUser sysUser) {
        Page<SysUser> page = new Page<>(sysUser.getPageNum(), sysUser.getPageSize());
        IPage list = sysUserMapper.getPageList(page, sysUser);
        return list;
    }

    /**
     * 修改保存用户信息
     *
     * @param sysUser 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean updateUser(SysUser sysUser) {
        int userId = sysUser.getUserId();
        // 删除用户与角色关联
        sysUserRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(sysUser);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", userId);
        sysUserMapper.update(sysUser, updateWrapper);
        return true;
    }

    /**
     * 新增保存用户信息
     *
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean insertUser(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        // 新增用户信息
        sysUserMapper.insert(sysUser);
        // 新增用户与角色管理
        insertUserRole(sysUser);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean deleteUserBatch(List<Integer> userIds) {
        // 批量删除用户与角色关联
        sysUserRoleMapper.deleteBatchUserIds(userIds);
        // 批量删除用户
        sysUserMapper.deleteBatchIds(userIds);
        return true;
    }

    /**
     * 新增用户信息
     *
     * @param sysUser
     */
    private void insertUserRole(SysUser sysUser) {
        Integer[] roleIds = sysUser.getRoleIds();
        if (!ObjectUtils.isEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Integer roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(sysUser.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            sysUserRoleMapper.insertUserRoleBatch(list);
        }
    }
}




