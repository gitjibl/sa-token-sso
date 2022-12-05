package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysRole;
import com.example.admin.service.SysRoleService;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    SysRoleService sysRoleService;


    @GetMapping("/getList")
    public R getList(SysRole sysRole) {
        IPage pageList = sysRoleService.getPageList(sysRole);
        return R.ok(pageList);
    }

    @GetMapping("/insert")
    public R insert(SysRole sysRole) {
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
}
