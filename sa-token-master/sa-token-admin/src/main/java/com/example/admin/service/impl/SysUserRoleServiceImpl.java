package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysUserRole;
import com.example.admin.service.SysUserRoleService;
import com.example.admin.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author jibl
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2022-12-08 15:13:49
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

}




