package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysRoleMenu;
import com.example.admin.service.SysRoleMenuService;
import com.example.admin.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author jibl
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2022-12-06 16:47:01
*/
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
    implements SysRoleMenuService{

}




