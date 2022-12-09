package com.example.admin.controller;

import com.alibaba.fastjson.JSON;
import com.example.admin.domain.SysMenu;
import com.example.admin.domain.SysUser;
import com.example.admin.domain.TreeSelect;
import com.example.admin.service.SysMenuService;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    SysMenuService sysMenuService;

    @GetMapping("/roleMenuTreeselect")
    public R roleMenuTreeselect(Integer roleId,String projectId) {
        Map map = new HashMap<>();
//        SysUser loginUser = JSON.parseObject(getLoginUser().toString(), SysUser.class);
        List<SysMenu> menus = sysMenuService.selectMenuListByProjectId(projectId);
        List<TreeSelect> treeMenus = sysMenuService.buildMenuTreeSelect(menus);
        List<Integer> checkedKeys = sysMenuService.selectMenuListByRoleId(roleId);
        map.put("checkedKeys", checkedKeys);
        map.put("menus", treeMenus);
        return R.ok(map);
    }
}
