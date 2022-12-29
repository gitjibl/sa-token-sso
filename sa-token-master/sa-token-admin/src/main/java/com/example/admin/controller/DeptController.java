package com.example.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.utils.R;
import com.example.system.domain.SysDept;
import com.example.system.domain.TreeSelect;
import com.example.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getList")
    public R getList(SysDept sysDept) {
        IPage pageList = sysDeptService.getPageList(sysDept);
        return R.ok(pageList);
    }

    /**
     * 查询部分列表
     *
     * @param sysDept
     * @return
     */
    @RequestMapping("/getPageList")
    public R getPageList(SysDept sysDept) {
        IPage pageList = sysDeptService.getPageList(sysDept);
        return R.ok(pageList);
    }

    @GetMapping("/insert")
    public R insert(SysDept sysDept) {
        boolean update = sysDeptService.save(sysDept);
        return update ? R.ok() : R.fail();
    }

    @GetMapping("/update")
    public R update(SysDept sysDept) {
        UpdateWrapper<SysDept> wrapper = new UpdateWrapper<>();
        wrapper.eq("dept_id", sysDept.getDeptId());
        boolean update = sysDeptService.update(sysDept, wrapper);
        return update ? R.ok() : R.fail();
    }

    @PostMapping("/delete")
    public R insert(@RequestBody String ids) {
        List<String> list = JSON.parseArray(ids, String.class);
        boolean update = sysDeptService.removeByIds(list);
        return update ? R.ok() : R.fail();
    }


    @GetMapping("/deptTreeSelect")
    public R deptTreeSelect(SysDept dept) {
        List<SysDept> depts = sysDeptService.selectDeptList(dept);
        List<TreeSelect> treeDepts = sysDeptService.buildDeptTreeSelect(depts);
        return R.ok(treeDepts);
    }


}