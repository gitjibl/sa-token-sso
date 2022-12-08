package com.example.admin.mapper;

import com.example.admin.domain.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jibl
 * @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
 * @createDate 2022-12-08 15:13:49
 * @Entity com.example.admin.domain.SysUserRole
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void insertBatchByRoleId(@Param("list") List<Integer> list, @Param("roleId") Integer roleId);

}




