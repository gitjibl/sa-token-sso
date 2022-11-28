package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysProject;
import com.example.admin.service.SysProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    SysProjectService sysProjectService;

    @RequestMapping("/getList")
    public IPage getList(SysProject sysProject){
        return sysProjectService.getPageList(sysProject);
    }
}
