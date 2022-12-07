package com.example.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysProject;
import com.example.admin.service.SysProjectService;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    SysProjectService sysProjectService;

    @GetMapping("/getPageList")
    public R getPageList(SysProject sysProject) {
        IPage pageList = sysProjectService.getPageList(sysProject);
        return R.ok(pageList);
    }

    @GetMapping("/getList")
    public R getList(SysProject sysProject) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List list = sysProjectService.list(queryWrapper);
        return R.ok(list);
    }

    @GetMapping("/insert")
    public R insert(SysProject sysProject) {
        boolean update = sysProjectService.save(sysProject);
        return update ? R.ok() : R.fail();
    }

    @GetMapping("/update")
    public R update(SysProject sysProject) {
        UpdateWrapper<SysProject> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id", sysProject.getProjectId());
        boolean update = sysProjectService.update(sysProject, wrapper);
        return update ? R.ok() : R.fail();
    }

    @PostMapping("/delete")
    public R insert(@RequestBody String ids) {
        List<String> list = JSON.parseArray(ids,String.class);
        boolean update = sysProjectService.removeByIds(list);
        return update ? R.ok() : R.fail();
    }


    @GetMapping("/updateStatus")
    public R updateStatus(SysProject sysProject) {
        UpdateWrapper<SysProject> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id", sysProject.getProjectId());
        boolean update = sysProjectService.update(sysProject, wrapper);
        return update ? R.ok() : R.fail();
    }


}
