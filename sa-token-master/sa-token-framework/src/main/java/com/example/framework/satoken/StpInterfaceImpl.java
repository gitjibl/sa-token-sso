package com.example.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.example.common.utils.SecurityUtil;
import com.example.system.domain.SysMenu;
import com.example.system.domain.SysRole;
import com.example.system.mapper.SysMenuMapper;
import com.example.system.mapper.SysRoleMapper;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Value("${admin.project-key}")
    private String projectId;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        List<SysMenu> menus = new ArrayList<>();
        if (SecurityUtil.isSuperAdmin()) {
            //超级管理员
            SysMenu sysMenu = new SysMenu();
            sysMenu.setProjectId(projectId);
            sysMenu.setStatus(0);
            menus = sysMenuMapper.selectMenuList(sysMenu);
        } else {
            menus = sysMenuMapper.selectMenusByUserIdAndProjectId(SecurityUtil.getLoginUserId(), projectId);
        }
        list = menus.stream().map(e -> e.getPerms()).filter(e -> !StringUtils.isEmpty(e)).collect(Collectors.toList());
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<SysRole> roles = sysRoleMapper.getLoginUserRoles(SecurityUtil.getLoginUserId(), projectId);
        List<String> list = new ArrayList<String>();
        list = roles.stream().map(e -> e.getRoleKey()).collect(Collectors.toList());
        return list;
    }

}
