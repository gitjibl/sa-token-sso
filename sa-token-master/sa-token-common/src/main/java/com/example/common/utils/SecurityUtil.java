package com.example.common.utils;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.common.core.domain.LoginUser;
import org.springframework.util.ObjectUtils;

public class SecurityUtil {

    /**
     * 获取登录用户
     */
    public static Object getLoginUser() {
        SaSession session = StpUtil.getTokenSession();
        Object obj = session.get("user");
        return obj;
    }

    /**
     * 获取登录用户名
     */
    public static String getLoginUserName() {
        SaSession session = StpUtil.getTokenSession();
        Object obj = session.get("user");
        if(ObjectUtils.isEmpty(obj)){
            throw new NotLoginException("Token已过期",null,"-3");
        }
        LoginUser loginUser = JSON.parseObject(obj.toString(), LoginUser.class);
        return loginUser.getUsername();
    }

    /**
     * 获取登录用户ID
     */
    public static Integer getLoginUserId() {
        SaSession session = StpUtil.getTokenSession();
        Object obj = session.get("user");
        if(ObjectUtils.isEmpty(obj)){
            throw new NotLoginException("Token已过期",null,"-3");
        }
        LoginUser loginUser = JSON.parseObject(obj.toString(), LoginUser.class);
        return loginUser.getUserId();
    }

    /**
     * 是否超级管理员
     * @return
     */
    public static boolean isSuperAdmin()
    {
        SaSession session = StpUtil.getTokenSession();
        Object obj = session.get("user");
        if(ObjectUtils.isEmpty(obj)){
            throw new NotLoginException("Token已过期",null,"-3");
        }
        LoginUser loginUser = JSON.parseObject(obj.toString(), LoginUser.class);
        Integer userId = loginUser.getUserId();
        return userId != null && 1 == userId;
    }
}
