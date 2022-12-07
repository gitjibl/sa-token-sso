package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author jibl
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-11-28 13:46:14
* @Entity com.example.admin.domain.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage getPageList(@Param("page") Page<SysRole> page, @Param("enity") SysRole sysRole);
}




