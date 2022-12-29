package com.example.admin.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.common.utils.R;
import com.example.common.utils.SecurityUtil;
import com.example.system.domain.SysMenu;
import com.example.system.domain.SysRole;
import com.example.system.domain.SysUser;
import com.example.system.mapper.SysMenuMapper;
import com.example.system.mapper.SysRoleMapper;
import com.example.system.mapper.SysUserMapper;
import com.example.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Value("${admin.project-key}")
    private String projectId;


    /**
     * 根据项目ID获取登录人的详情信息
     *
     * @param projectId
     * @return
     */
    @RequestMapping("/getLoginUser")
    public Object getLoginUser(@RequestParam(required = true) String projectId) {
        /**
         * 调用StpUtil.getPermissionList() 才走 StpInterfaceImpl？
         */
//        List<String> permissionList = StpUtil.getPermissionList();

        Integer userId = SecurityUtil.getLoginUserId();
        SysUser sysUser = sysUserMapper.getLoginUser(userId);
        Set<String> menus = new HashSet<>();
        if (SecurityUtil.isSuperAdmin()) {
            //超级管理员
            menus.add("*:*:*");
            sysUser.setRoleKeys(new String[]{"super-admin"});
        } else {
            List<SysRole> roles = sysRoleMapper.getLoginUserRoles(userId, projectId);
            String[] roleKeys = roles.stream().map(e -> e.getRoleKey()).toArray(String[]::new);
            sysUser.setRoleKeys(roleKeys);
            roles.forEach(item -> {
                List<SysMenu> menu = sysMenuMapper.getLoginUserRoleMenus(item.getRoleId());
                item.setMenus(menu);
                menus.addAll(menu.stream().map(e -> e.getPerms()).collect(Collectors.toList()).stream().filter(e -> !ObjectUtils.isEmpty(e)).collect(Collectors.toList()));
            });
            sysUser.setRoles(roles);
        }
        sysUser.setMenus(menus.toArray(new String[0]));
        return sysUser;
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R getRouters() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        StpUtil.getSessionByLoginId(tokenInfo.getLoginId());
        SaSession session = StpUtil.getTokenSession();
        SysUser loginUser = JSON.parseObject(session.get("user").toString(), SysUser.class);
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(loginUser.getUserId(), projectId);
        return R.ok(sysMenuService.buildMenus(menus));
    }
}
