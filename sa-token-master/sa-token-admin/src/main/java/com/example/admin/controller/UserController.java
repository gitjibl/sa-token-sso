package com.example.admin.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysProject;
import com.example.admin.domain.SysRole;
import com.example.admin.domain.SysUser;
import com.example.admin.domain.SysUserRole;
import com.example.admin.service.SysRoleService;
import com.example.admin.service.SysUserRoleService;
import com.example.admin.service.SysUserService;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserRoleService sysUserRoleService;


    @RequestMapping("/getLoginUserInfo")
    public Object getLoginUserInfo() {
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


    @GetMapping("/getPageList")
    public R getPageList(SysUser sysUser) {
        IPage pageList = sysUserService.getPageList(sysUser);
        return R.ok(pageList);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(SysUser sysUser) {
        Integer userId = sysUser.getUserId();
        List<SysRole> roles = sysRoleService.list();
        sysUser.setRoles(roles);
        if (!ObjectUtils.isEmpty(userId)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", userId);
            List<SysUserRole> userRoles = sysUserRoleService.list(queryWrapper);
            List<Integer> collect = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            sysUser.setRoleIds(collect.toArray(new Integer[collect.size()]));
        }
        return R.ok(sysUser);
    }

    @GetMapping("/insert")
    public R insert(SysUser sysUser) {
        boolean update = sysUserService.insertUser(sysUser);
        return update ? R.ok() : R.fail();
    }

    @GetMapping("/update")
    public R update(SysUser sysUser) {
        boolean update = sysUserService.updateUser(sysUser);
        return update ? R.ok() : R.fail();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody String ids) {
        List<Integer> userIds = JSON.parseArray(ids, Integer.class);
        boolean update = sysUserService.deleteUserBatch(userIds);
        return update ? R.ok() : R.fail();
    }


    @GetMapping("/resetUserPwd")
    public R resetUserPwd(SysUser sysUser) {
        sysUser.setPw(sysUser.getPassword());
        String password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes(StandardCharsets.UTF_8));
        sysUser.setPassword(password);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", sysUser.getUserId());
        boolean update = sysUserService.update(sysUser,updateWrapper);
        return update ? R.ok() : R.fail();
    }

    /**
     * 更新用户角色关联
     * @param sysUser
     * @return
     */
    @PostMapping("/updateUserRole")
    public R updateUserRole(@RequestBody SysUser sysUser) {
        boolean update = sysUserService.updateUserRole(sysUser);
        return update ? R.ok() : R.fail();
    }


}
