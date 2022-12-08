package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysRole;
import com.example.admin.domain.SysRoleMenu;
import com.example.admin.domain.SysUser;
import com.example.admin.domain.SysUserRole;
import com.example.admin.mapper.SysRoleMenuMapper;
import com.example.admin.mapper.SysUserRoleMapper;
import com.example.admin.service.SysRoleService;
import com.example.admin.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author jibl
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2022-11-28 13:46:14
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    
    @Override
    public IPage getPageList(SysRole sysRole) {
        Page<SysRole> page = new Page<>(sysRole.getPageNum(), sysRole.getPageSize());
        IPage list = sysRoleMapper.getPageList(page, sysRole);
        return list;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean updateRoleMenu(SysRole sysRole) {
        sysRoleMenuMapper.deleteById(sysRole.getRoleId());
                Integer[] menuIds = sysRole.getMenuIds();
        for (Integer menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return true;
    }

    @Override
    public IPage getAuthUsers(SysUser user) {
        Page<SysUser> page = new Page<>(user.getPageNum(), user.getPageSize());
        IPage list = sysRoleMapper.getAuthUsers(page, user);
        return list;
    }

    @Override
    public IPage getUnAuthUsers(SysUser user) {
        Page<SysUser> page = new Page<>(user.getPageNum(), user.getPageSize());
        IPage list = sysRoleMapper.getUnAuthUsers(page, user);
        return list;
    }

    @Override
    public void authUser(List<Integer> list, Integer roleId) {
        sysUserRoleMapper.insertBatchByRoleId(list,roleId);
    }

    @Override
    public void cancelAuthUser(List<Integer> list, Integer roleId) {
        list.forEach(item->{
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("user_id", item);
            columnMap.put("role_id", roleId);
            sysUserRoleMapper.deleteByMap(columnMap);
        });
    }
}




