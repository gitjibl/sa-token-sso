package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.domain.TreeSelect;

import java.util.List;

/**
* @author jibl
* @description 针对表【sys_dept(部门表)】的数据库操作Service
* @createDate 2022-11-28 11:35:31
*/
public interface SysDeptService extends IService<SysDept> {

    IPage getPageList(SysDept sysDept);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);
}
