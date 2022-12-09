package com.example.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysRole;
import com.example.admin.domain.SysUser;
import com.example.admin.service.SysRoleService;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    SysRoleService sysRoleService;


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


    @GetMapping("/insert")
    public R insert(SysRole sysRole) {
        sysRole.setCreateBy(getLoginUserName());
        sysRole.setCreateTime(new Date());
        boolean update = sysRoleService.save(sysRole);
        return update ? R.ok() : R.fail();
    }

    @GetMapping("/update")
    public R update(SysRole sysRole) {
        UpdateWrapper<SysRole> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id", sysRole.getRoleId());
        boolean update = sysRoleService.update(sysRole, wrapper);
        return update ? R.ok() : R.fail();
    }

    @PostMapping("/updateRoleMenu")
    public R updateRoleMenu(@RequestBody SysRole sysRole) {
        boolean update = sysRoleService.updateRoleMenu(sysRole);
        return update ? R.ok() : R.fail();
    }

    /**
     * 查询分配用户列表
     */
    @GetMapping("/authUser/allocatedList")
    public R allocatedList(SysUser user,boolean flag) {
        IPage pageList = null;
        if(flag){
            pageList = sysRoleService.getAuthUsers(user);
        }else{
            pageList = sysRoleService.getUnAuthUsers(user);
        }
        return R.ok(pageList);
    }

    /**
     * 角色授权用户
     * @param params
     * @return
     */
    @PostMapping("authUser")
    public R authUser(@RequestBody String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        List<Integer> list = JSON.parseArray(jsonObject.get("ids").toString(),Integer.class);
        Integer roleId = jsonObject.getInteger("roleId");
        sysRoleService.authUser(list,roleId);
        return R.ok();
    }

    /**
     * 角色取消授权用户
     * @param params
     * @return
     */
    @PostMapping("cancelAuthUser")
    public R cancelAuthUser(@RequestBody String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        List<Integer> list = JSON.parseArray(jsonObject.get("ids").toString(),Integer.class);
        Integer roleId = jsonObject.getInteger("roleId");
        sysRoleService.cancelAuthUser(list,roleId);
        return R.ok();
    }



}
