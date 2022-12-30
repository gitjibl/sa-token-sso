package com.example.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import com.example.common.utils.SecurityUtil;
import com.example.system.domain.SysRole;
import com.example.system.domain.SysUser;
import com.example.system.domain.TreeSelect;
import com.example.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    SysRoleService sysRoleService;


    @SaCheckPermission("system:role:list")
    @GetMapping("/getPageList")
    public R getPageList(SysRole sysRole) {
        IPage pageList = sysRoleService.getPageList(sysRole);
        return R.ok(pageList);
    }

    @GetMapping("/getList")
    public R getList(SysRole sysRole) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List<SysRole> list = sysRoleService.list(queryWrapper);
        return R.ok(list);
    }


    @SaCheckPermission("system:role:add")
    @GetMapping("/insert")
    public R insert(SysRole sysRole) {
        sysRole.setCreateBy(SecurityUtil.getLoginUserName());
        sysRole.setCreateTime(new Date());
        boolean update = sysRoleService.save(sysRole);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:role:edit")
    @GetMapping("/update")
    public R update(SysRole sysRole) {
        UpdateWrapper<SysRole> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id", sysRole.getRoleId());
        boolean update = sysRoleService.update(sysRole, wrapper);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:role:delete")
    @PostMapping("/delete")
    public R delete(@RequestBody String ids) {
        List<Integer> roleIds = JSON.parseArray(ids, Integer.class);
        boolean update = sysRoleService.deleteRoleBatch(roleIds);
        return update ? R.ok() : R.fail();
    }

    /**
     * 更改角色权限
     * @param sysRole
     * @return
     */
    @SaCheckPermission("system:role:dataScope")
    @PostMapping("/updateRoleMenu")
    public R updateRoleMenu(@RequestBody SysRole sysRole) {
        boolean update = sysRoleService.updateRoleMenu(sysRole);
        return update ? R.ok() : R.fail();
    }

    /**
     * 查询分配用户列表
     */
    @GetMapping("/getAuthUserList")
    public R getAuthUserList(SysUser user, boolean flag) {
        IPage pageList = null;
        if (flag) {
            pageList = sysRoleService.getAuthUsers(user);
        } else {
            pageList = sysRoleService.getUnAuthUsers(user);
        }
        return R.ok(pageList);
    }


    /**
     * 角色授权用户
     * @param sysRole
     * @return
     */
    @SaCheckPermission("system:role:authUser")
    @PostMapping("authUser")
    public R authUser(@RequestBody SysRole sysRole) {
        sysRoleService.authUser(sysRole);
        return R.ok();
    }

    /**
     * 角色取消授权用户
     *
     * @param params
     * @return
     */
    @SaCheckPermission("system:role:authUser")
    @PostMapping("cancelAuthUser")
    public R cancelAuthUser(@RequestBody String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        List<Integer> list = JSON.parseArray(jsonObject.get("ids").toString(), Integer.class);
        Integer roleId = jsonObject.getInteger("roleId");
        sysRoleService.cancelAuthUser(list, roleId);
        return R.ok();
    }

    /**
     * 获取角色树
     * 获取用户角色列表
     *
     * @param userId
     * @return
     */
    @GetMapping("roleTreeselect")
    public R roleTreeselect(Integer userId) {
        Map map = new HashMap<>();
        List<TreeSelect> roles = sysRoleService.roleTreeSelect();
        List<Integer> checkedKeys = sysRoleService.selectRoleListByUserId(userId);
        map.put("roles", roles);
        map.put("checkedKeys", checkedKeys);
        return R.ok(map);
    }


}
