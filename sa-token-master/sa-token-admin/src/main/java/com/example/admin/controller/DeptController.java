package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysDept;
import com.example.admin.domain.TreeSelect;
import com.example.admin.service.SysDeptService;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统部门管理
 *
 * @author huanyi
 * @date 2022-11-29
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    SysDeptService sysDeptService;

    /**
     * 查询部分列表
     *
     * @param sysDept
     * @return
     */
    @GetMapping("/getList")
    public R getList(SysDept sysDept) {
        IPage pageList = sysDeptService.getPageList(sysDept);
        return R.ok(pageList);
    }


    @GetMapping("/deptTreeSelect")
    public R deptTreeSelect(SysDept dept){
        List<SysDept> depts = sysDeptService.selectDeptList(dept);
        List<TreeSelect> treeDepts = sysDeptService.buildDeptTreeSelect(depts);
        return R.ok(treeDepts);
    }






}
