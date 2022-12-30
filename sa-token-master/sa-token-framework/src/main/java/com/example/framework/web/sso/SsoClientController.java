package com.example.framework.web.sso;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.common.utils.R;
import com.example.common.utils.SecurityUtil;
import com.example.system.domain.SysMenu;
import com.example.system.domain.SysProject;
import com.example.system.domain.SysRole;
import com.example.system.domain.SysUser;
import com.example.system.mapper.SysMenuMapper;
import com.example.system.mapper.SysProjectMapper;
import com.example.system.mapper.SysRoleMapper;
import com.example.system.mapper.SysUserMapper;
import com.example.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sa-Token-SSO Client端 Controller
 *
 */
@RestController
public class SsoClientController {

    @Autowired
    SysProjectMapper sysProjectMapper;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Value("${admin.project-key}")
    private String projectId;


    /**
     * 根据项目ID获取登录人的详情信息
     *
     * @param projectId
     * @return
     */
    @RequestMapping("/getLoginUser")
    public Object getLoginUser(@RequestParam(required = true) String projectId) {
        /**
         * 调用StpUtil.getPermissionList() 才走 StpInterfaceImpl？
         */
//        List<String> permissionList = StpUtil.getPermissionList();
        SysProject project = sysProjectMapper.selectById(projectId);
        Integer userId = SecurityUtil.getLoginUserId();
        SysUser sysUser = sysUserMapper.getLoginUser(userId);
        if (SecurityUtil.isSuperAdmin()) {
            //超级管理员
            sysUser.setPerms(new String[]{"*:*:*"});
            sysUser.setRoleKeys(new String[]{"super-admin"});
            List<SysMenu> menus = sysMenuMapper.selectMenusByUserIdAndProjectId(null, projectId);
            sysUser.setMenus(menus);
        } else {
            List<SysRole> roles = sysRoleMapper.getLoginUserRoles(userId, projectId);
            String[] roleKeys = roles.stream().map(e -> e.getRoleKey()).toArray(String[]::new);
            sysUser.setRoleKeys(roleKeys);
            sysUser.setRoles(roles);
            List<SysMenu> menus = sysMenuMapper.selectMenusByUserIdAndProjectId(userId, projectId);
            sysUser.setMenus(menus);
            sysUser.setPerms(menus.stream().map(e -> e.getPerms()).filter(e -> !StringUtils.isEmpty(e)).collect(Collectors.toList()).toArray(new String[0]));
        }
        project.setUser(sysUser);
        return project;
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R getRouters() {
        Integer userId = SecurityUtil.getLoginUserId();
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(userId, projectId);
        return R.ok(sysMenuService.buildMenus(menus));
    }

    // 首页
    @RequestMapping("/")
    public String index() {
        String str = "<h2>应用端</h2>" + "<p>当前会话是否登录：" + StpUtil.isLogin() + "</p>" + "<p><a href=\"javascript:location.href='/sso/login?back=' + encodeURIComponent(location.href);\">登录</a> " + "<a href='/sso/logout?back=self'>注销</a></p>";
        return str;
    }

    /**
     * SSO-Client端：处理所有SSO相关请求
     * http://{host}:{port}/sso/login			-- Client端登录地址，接受参数：back=登录后的跳转地址
     * http://{host}:{port}/sso/logout			-- Client端单点注销地址（isSlo=true时打开），接受参数：back=注销后的跳转地址
     * http://{host}:{port}/sso/logoutCall		-- Client端单点注销回调地址（isSlo=true时打开），此接口为框架回调，开发者无需关心
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoProcessor.instance.clientDister();
    }

}
