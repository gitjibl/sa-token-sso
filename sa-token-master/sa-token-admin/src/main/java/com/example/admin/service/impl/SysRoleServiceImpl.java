package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysRole;
import com.example.admin.domain.SysRoleMenu;
import com.example.admin.mapper.SysRoleMenuMapper;
import com.example.admin.service.SysRoleService;
import com.example.admin.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
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

    
    @Override
    public IPage getPageList(SysRole sysRole) {
        Page<SysRole> page = new Page<>(sysRole.getPageNum(), sysRole.getPageSize());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(sysRole.getRoleName())) {
            queryWrapper.like("role_name", sysRole.getRoleName());
        }
        if (!ObjectUtils.isEmpty(sysRole.getRoleKey())) {
            queryWrapper.like("role_key", sysRole.getRoleKey());
        }
        if (!ObjectUtils.isEmpty(sysRole.getStatus())) {
            queryWrapper.eq("status", sysRole.getStatus());
        }
        queryWrapper.orderByAsc("role_sort");
        IPage<SysRole> list = sysRoleMapper.selectPage(page, queryWrapper);
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
}




