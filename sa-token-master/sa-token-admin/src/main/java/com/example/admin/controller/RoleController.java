package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysRole;
import com.example.admin.service.SysRoleService;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

}
