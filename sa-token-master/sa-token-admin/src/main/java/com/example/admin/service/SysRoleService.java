package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jibl
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-11-28 13:46:14
*/
public interface SysRoleService extends IService<SysRole> {

    IPage getPageList(SysRole sysRole);
}
