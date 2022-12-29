package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.domain.SysRole;
import com.example.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jibl
 * @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
 * @createDate 2022-11-28 13:46:14
 * @Entity com.example.admin.domain.SysRole
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    IPage getPageList(@Param("page") Page<SysRole> page, @Param("e") SysRole sysRole);

    IPage getAuthUsers(@Param("page") Page<SysUser> page, @Param("e") SysUser user);

    IPage getUnAuthUsers(@Param("page") Page<SysUser> page, @Param("e") SysUser user);

    List<SysRole> selectRoleList();

    void deleteRoleBatch(List<Integer> roleIds);

    /**
     * 获取登录人角色信息
     *
     * @param userId
     * @param projectId
     * @return
     */
    List<SysRole> getLoginUserRoles(@Param("userId") Integer userId, @Param("projectId") String projectId);
}




