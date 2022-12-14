package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysRoleDept;
import com.example.admin.service.SysRoleDeptService;
import com.example.admin.mapper.SysRoleDeptMapper;
import org.springframework.stereotype.Service;

/**
* @author jibl
* @description 针对表【sys_role_dept(角色和部门关联表)】的数据库操作Service实现
* @createDate 2022-12-12 15:30:54
*/
@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept>
    implements SysRoleDeptService{

}




