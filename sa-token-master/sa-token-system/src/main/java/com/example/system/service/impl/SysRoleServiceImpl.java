package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.ServiceException;
import com.example.system.domain.*;
import com.example.system.mapper.SysRoleDeptMapper;
import com.example.system.mapper.SysRoleMapper;
import com.example.system.mapper.SysRoleMenuMapper;
import com.example.system.mapper.SysUserRoleMapper;
import com.example.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jibl
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2022-11-28 13:46:14
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysRoleDeptMapper sysRoleDeptMapper;


    @Override
    public IPage getPageList(SysRole sysRole) {
        Page<SysRole> page = new Page<>(sysRole.getPageNum(), sysRole.getPageSize());
        IPage list = sysRoleMapper.getPageList(page, sysRole);
        return list;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean updateRoleMenu(SysRole sysRole) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",sysRole.getRoleId());
        sysRoleMenuMapper.delete(queryWrapper);
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        Integer[] menuIds = sysRole.getMenuIds();
        for (Integer menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenus.add(sysRoleMenu);
        }
        if(!sysRoleMenus.isEmpty()){
            sysRoleMenuMapper.insertRoleMenuBatch(sysRoleMenus);
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
    public void authUser(SysRole sysRole) {
        //更新用户角色关联
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        List<Integer> userIds = Arrays.asList(sysRole.getUserIds());
        userIds.forEach(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(item);
            sysUserRole.setRoleId(sysRole.getRoleId());
            sysUserRoles.add(sysUserRole);
        });
        sysUserRoleMapper.insertUserRoleBatch(sysUserRoles);
    }

    @Override
    public void cancelAuthUser(List<Integer> list, Integer roleId) {
        list.forEach(item -> {
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("user_id", item);
            columnMap.put("role_id", roleId);
            sysUserRoleMapper.deleteByMap(columnMap);
        });
    }

    @Override
    public List<TreeSelect> roleTreeSelect() {
        List<TreeSelect> list = new ArrayList<>();
        List<SysRole> roles = sysRoleMapper.selectRoleList();
        Map<String, List<SysRole>> collect = roles.stream().collect(Collectors.groupingBy(e -> e.getProjectId() + "-" + e.getProjectName()));
        collect.forEach((k, v) -> {
            TreeSelect treeSelect = new TreeSelect();
            String[] split = k.split("-");
            treeSelect.setId(split[0]);
            treeSelect.setLabel(split[1]);
            treeSelect.setChildren(v.stream().map(TreeSelect::new).collect(Collectors.toList()));
            list.add(treeSelect);
        });
        return list;
    }

    @Override
    public List<Integer> selectRoleListByUserId(Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        List<SysUserRole> list = sysUserRoleMapper.selectList(queryWrapper);
        List<Integer> collect = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return collect;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean deleteRoleBatch(List<Integer> roleIds) {
        for (Integer roleId : roleIds)
        {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_id",roleId);
            List<SysUserRole> userRoles = sysUserRoleMapper.selectList(queryWrapper);
            if (!userRoles.isEmpty())
            {
                throw new ServiceException("角色已分配用户，不能删除");
            }
        }

        // 删除角色与菜单关联
        sysRoleMenuMapper.deleteRoleMenuBatch(roleIds);
        // 删除角色与部门关联
        sysRoleDeptMapper.deleteRoleDeptBatch(roleIds);
        //删除角色
        sysRoleMapper.deleteRoleBatch(roleIds);
        return true;
    }
}




