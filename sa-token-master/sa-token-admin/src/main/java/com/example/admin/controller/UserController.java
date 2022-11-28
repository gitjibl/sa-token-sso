package com.example.admin.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/getUserInfo")
    public Object getUserInfo(){
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        /**
         * 调用StpUtil.getPermissionList() 才走 StpInterfaceImpl？
         */
//        List<String> permissionList = StpUtil.getPermissionList();
        StpUtil.getSessionByLoginId(tokenInfo.getLoginId());
        long tokenTimeout = StpUtil.getTokenTimeout();
        SaSession session = StpUtil.getTokenSession();
        Object o = session.get("user");
        return o;
    }
}
