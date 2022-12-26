package com.example.admin.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.admin.domain.SysMenu;
import com.example.admin.domain.SysRole;
import com.example.admin.domain.SysUser;
import com.example.admin.mapper.SysMenuMapper;
import com.example.admin.mapper.SysRoleMapper;
import com.example.admin.mapper.SysUserMapper;
import com.example.admin.service.SysMenuService;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

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


    @RequestMapping("/getLoginUser")
    public Object getLoginUser(String projectId) {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        /**
         * 调用StpUtil.getPermissionList() 才走 StpInterfaceImpl？
         */
//        List<String> permissionList = StpUtil.getPermissionList();
        StpUtil.getSessionByLoginId(tokenInfo.getLoginId());
        SaSession session = StpUtil.getTokenSession();
        SysUser loginUser = JSON.parseObject(session.get("user").toString(),SysUser.class);
        SysUser sysUser = sysUserMapper.getLoginUser(loginUser.getUserId());
        List<SysRole> roles = sysRoleMapper.getLoginUserRoles(loginUser.getUserId(), projectId);
        roles.forEach(item -> {
            item.setMenus(sysMenuMapper.getLoginUserRoleMenus(item.getRoleId()));
        });
        sysUser.setRoles(roles);
        return sysUser;
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R getRouters()
    {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        StpUtil.getSessionByLoginId(tokenInfo.getLoginId());
        SaSession session = StpUtil.getTokenSession();
        SysUser loginUser = JSON.parseObject(session.get("user").toString(),SysUser.class);
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(loginUser.getUserId());
        return R.ok(sysMenuService.buildMenus(menus));
    }
}
