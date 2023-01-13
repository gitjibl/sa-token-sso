package com.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.SysDept;
import com.example.system.domain.TreeSelect;


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

    /**
     * 是否存在子节点
     * @param deptId
     * @return
     */
    boolean hasChildByDeptId(Integer deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkDeptExistUser(Integer deptId);

}
