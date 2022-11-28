package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysUser;
import com.example.admin.service.SysUserService;
import com.example.admin.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author jibl
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-11-28 11:35:12
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




