package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.domain.SysDept;


import java.util.List;

/**
* @author jibl
* @description 针对表【sys_dept(部门表)】的数据库操作Mapper
* @createDate 2022-11-28 11:35:31
* @Entity com.example.admin.domain.SysDept
*/
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);
}




