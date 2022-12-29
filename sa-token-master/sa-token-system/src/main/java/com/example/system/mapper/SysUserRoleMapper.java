package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.domain.SysUserRole;


import java.util.List;

/**
 * @author jibl
 * @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
 * @createDate 2022-12-08 15:13:49
 * @Entity com.example.admin.domain.SysUserRole
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量新增用户角色信息
     *
     * @param list
     */
    void insertUserRoleBatch(List<SysUserRole> list);

    /**
     * 批量删除用户与角色关联
     * @param userIds
     */
    void deleteUserBatch(List<Integer> userIds);
}




