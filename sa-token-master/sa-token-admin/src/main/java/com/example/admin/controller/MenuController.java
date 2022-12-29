package com.example.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import com.example.common.utils.SecurityUtil;
import com.example.system.domain.SysMenu;
import com.example.system.domain.TreeSelect;
import com.example.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    SysMenuService sysMenuService;

    @GetMapping("/roleMenuTreeselect")
    public R roleMenuTreeselect(Integer roleId, SysMenu sysMenu) {
        Map map = new HashMap<>();
//        SysUser loginUser = JSON.parseObject(getLoginUser().toString(), SysUser.class);
        List<SysMenu> menus = sysMenuService.selectMenuList(sysMenu);
        List<TreeSelect> treeMenus = sysMenuService.buildMenuTreeSelect(menus);
        List<Integer> checkedKeys = sysMenuService.selectMenuListByRoleId(roleId);
        map.put("checkedKeys", checkedKeys);
        map.put("menus", treeMenus);
        return R.ok(map);
    }

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    @GetMapping("/getList")
    public R getList(SysMenu sysMenu) {
        List<SysMenu> menus = sysMenuService.selectMenuList(sysMenu);
        return R.ok(menus);
    }

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    @GetMapping("/insert")
    public R insert(SysMenu sysMenu) {
        sysMenu.setCreateBy(SecurityUtil.getLoginUserName());
        sysMenu.setCreateTime(new Date());
        boolean save = sysMenuService.save(sysMenu);
        return save ? R.ok() : R.fail();
    }

    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    @GetMapping("/update")
    public R update(SysMenu sysMenu) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("menu_id", sysMenu.getMenuId());
        boolean update = sysMenuService.update(sysMenu, updateWrapper);
        return update ? R.ok() : R.fail();
    }

    /**
     * 删除菜单
     *
     * @param sysMenu
     * @return
     */
    @GetMapping("/delete")
    public R delete(SysMenu sysMenu) {
        if(sysMenuService.hasChildByMenuId(sysMenu.getMenuId())){
            return R.fail("存在子菜单,不允许删除");
        }
        if(sysMenuService.checkMenuExistRole(sysMenu.getMenuId())){
            return R.fail("菜单已分配,不允许删除");
        }
        sysMenuService.removeById(sysMenu.getMenuId());
        return R.ok();
    }
}
