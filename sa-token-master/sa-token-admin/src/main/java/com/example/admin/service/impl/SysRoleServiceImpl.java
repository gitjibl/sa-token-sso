package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysRole;
import com.example.admin.service.SysRoleService;
import com.example.admin.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author jibl
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2022-11-28 11:35:46
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

}




