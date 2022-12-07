package com.example.common.core.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.common.core.domain.LoginUser;
import com.example.common.utils.DateUtils;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * web层通用数据处理
 *
 * @author jibl
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 获取登录用户
     */
    public Object getLoginUser() {
        SaSession session = StpUtil.getTokenSession();
        Object obj = session.get("user");
        return obj;
    }

    /**
     * 获取登录用户名
     */
    public String getLoginUserName() {
        SaSession session = StpUtil.getTokenSession();
        Object obj = session.get("user");
        LoginUser loginUser = JSON.parseObject(obj.toString(), LoginUser.class);
        return loginUser.getUsername();
    }



}
