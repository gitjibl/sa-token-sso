package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author jibl
 * @description 针对表【sys_user】的数据库操作Mapper
 * @createDate 2022-11-28 11:35:12
 * @Entity com.example.admin.domain.SysUser
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage getPageList(@Param("page") Page<SysUser> page, @Param("e") SysUser sysUser);
}




