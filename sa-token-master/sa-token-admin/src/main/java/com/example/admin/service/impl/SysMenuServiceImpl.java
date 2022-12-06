package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysMenu;
import com.example.admin.domain.SysRole;
import com.example.admin.domain.SysUser;
import com.example.admin.domain.TreeSelect;
import com.example.admin.mapper.SysRoleMapper;
import com.example.admin.service.SysMenuService;
import com.example.admin.mapper.SysMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jibl
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2022-11-28 11:35:36
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
        implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    /**
     * 查询系统菜单列表
     *
     * @param userId 登录用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(Integer userId) {
        SysMenu menu = new SysMenu();
        List<SysMenu> menuList = null;
        menuList = sysMenuMapper.selectMenuList(menu);
        // 管理员显示所有菜单信息
//        if (SysUser.isAdmin(userId)) {
//            menuList = sysMenuMapper.selectMenuList(menu);
//        } else {
//            menu.getParams().put("userId", userId);
//            menuList = sysMenuMapper.selectMenuListByUserId(menu);
//        }
        return menuList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
//        List<SysMenu> menuTrees = buildMenuTree(menus);
        List<SysMenu> menuTrees = new ArrayList<>();
        Map<String, List<SysMenu>> collect = menus.stream().collect(Collectors.groupingBy(SysMenu::getProjectName));

        collect.forEach((key,value)->{
            SysMenu sysMenu = new SysMenu();
            sysMenu.setMenuId((int) Math.random());
            sysMenu.setMenuName(key);
            sysMenu.setChildren(buildMenuTree(menus));
            menuTrees.add(sysMenu);
        });

        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Integer> selectMenuListByRoleId(Integer roleId) {
        return sysMenuMapper.selectMenuListByRoleId(roleId);
    }


    /**
     * 构建前端所需要树结构
     *  1、判断是否为顶级节点
     *  2、递归遍历顶级节点
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<Integer> tempList = new ArrayList<Integer>();
        for (SysMenu dept : menus) {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }


    /**
     * 递归列表
     *
     * @param list 遍历列表
     * @param t  父节点？
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }
}




