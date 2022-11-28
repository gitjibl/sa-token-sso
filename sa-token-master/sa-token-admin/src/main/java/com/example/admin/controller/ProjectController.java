package com.example.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysProject;
import com.example.admin.service.SysProjectService;
import com.example.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    SysProjectService sysProjectService;

    @RequestMapping("/getList")
    public IPage getList(SysProject sysProject) {
        return sysProjectService.getPageList(sysProject);
    }

    @RequestMapping("/insert")
    public R insert(SysProject sysProject) {
        boolean update = sysProjectService.save(sysProject);
        return update ? R.ok() : R.fail();
    }

    @RequestMapping("/update")
    public R update(SysProject sysProject) {
        UpdateWrapper<SysProject> wrapper = new UpdateWrapper<>();
        wrapper.eq("project_id", sysProject.getProjectId());
        boolean update = sysProjectService.update(sysProject, wrapper);
        return update ? R.ok() : R.fail();
    }

    @RequestMapping("/delete")
    public R insert(@RequestBody String ids) {
        List<String> list = JSON.parseArray(ids,String.class);
        boolean update = sysProjectService.removeByIds(list);
        return update ? R.ok() : R.fail();
    }


}
