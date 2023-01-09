package com.example.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import com.example.system.domain.SysRole;
import com.example.system.domain.SysUser;
import com.example.system.domain.SysUserRole;
import com.example.system.service.SysRoleService;
import com.example.system.service.SysUserRoleService;
import com.example.system.service.SysUserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
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



    @SaCheckPermission("system:user:list")
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

    @SaCheckPermission("system:user:add")
    @GetMapping("/insert")
    public R insert(SysUser sysUser) {
        boolean update = sysUserService.insertUser(sysUser);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:user:edit")
    @GetMapping("/update")
    public R update(SysUser sysUser) {
        boolean update = sysUserService.updateUser(sysUser);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:user:delete")
    @PostMapping("/delete")
    public R delete(@RequestBody String ids) {
        List<Integer> userIds = JSON.parseArray(ids, Integer.class);
        boolean update = sysUserService.deleteUserBatch(userIds);
        return update ? R.ok() : R.fail();
    }


    @SaCheckPermission("system:user:resetPwd")
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
    @SaCheckPermission("system:user:authRole")
    @PostMapping("/updateUserRole")
    public R updateUserRole(@RequestBody SysUser sysUser) {
        boolean update = sysUserService.updateUserRole(sysUser);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user)
    {
        System.out.println(123456);
    }

    @PostMapping("/importData")
    public R importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        String message = "导入成功！";
        return R.ok(message);
    }
}
