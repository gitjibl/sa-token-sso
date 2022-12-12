package com.example.admin.mapper;

import com.example.admin.domain.SysRoleDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author jibl
* @description 针对表【sys_role_dept(角色和部门关联表)】的数据库操作Mapper
* @createDate 2022-12-12 15:30:54
* @Entity com.example.admin.domain.SysRoleDept
*/
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

    /**
     * 批量删除角色部门关联信息
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    void deleteRoleDeptBatch(List<Integer> roleIds);
}




