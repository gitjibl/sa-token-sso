package com.example.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.constant.UserConstants;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import com.example.common.utils.SecurityUtil;
import com.example.system.domain.SysDept;
import com.example.system.domain.TreeSelect;
import com.example.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 系统部门管理
 *
 * @author huanyi
 * @date 2022-11-29
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {

    @Autowired
    SysDeptService sysDeptService;

    @SaCheckPermission("system:dept:list")
    @GetMapping("/getList")
    public R getList(SysDept sysDept) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(sysDept.getDeptName())) {
            queryWrapper.like("dept_name", sysDept.getDeptName());
        }
        if (!ObjectUtils.isEmpty(sysDept.getStatus())) {
            queryWrapper.eq("status", sysDept.getStatus());
        }
        List<SysDept> list = sysDeptService.list(queryWrapper);
        return R.ok(list);
    }

    @SaCheckPermission("system:dept:add")
    @GetMapping("/insert")
    public R insert(SysDept sysDept) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper();
        queryWrapper.eq("dept_id", sysDept.getParentId());
        List<SysDept> list = sysDeptService.list(queryWrapper);
        if (!list.isEmpty()) {
            sysDept.setAncestors(list.get(0).getAncestors() + "," + sysDept.getParentId());
        }
        sysDept.setCreateBy(SecurityUtil.getLoginUserName());
        sysDept.setCreateTime(new Date());
        boolean update = sysDeptService.save(sysDept);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:dept:edit")
    @GetMapping("/update")
    public R update(SysDept sysDept) {
        UpdateWrapper<SysDept> wrapper = new UpdateWrapper<>();
        wrapper.eq("dept_id", sysDept.getDeptId());
        boolean update = sysDeptService.update(sysDept, wrapper);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:dept:delete")
    @GetMapping("/delete")
    public R insert(Integer deptId) {
        if (sysDeptService.hasChildByDeptId(deptId)) {
            return R.fail("存在下级部门,不允许删除");
        }
        if (sysDeptService.checkDeptExistUser(deptId)) {
            return R.fail("部门存在用户,不允许删除");
        }
        boolean update = sysDeptService.removeById(deptId);
        return update ? R.ok() : R.fail();
    }


    @GetMapping("/deptTreeSelect")
    public R deptTreeSelect(SysDept dept) {
        List<SysDept> depts = sysDeptService.selectDeptList(dept);
        List<TreeSelect> treeDepts = sysDeptService.buildDeptTreeSelect(depts);
        return R.ok(treeDepts);
    }


}