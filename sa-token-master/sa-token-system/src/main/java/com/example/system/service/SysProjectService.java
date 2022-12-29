package com.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.SysProject;


/**
* @author jibl
* @description 针对表【sys_project】的数据库操作Service
* @createDate 2022-11-28 13:46:06
*/
public interface SysProjectService extends IService<SysProject> {

    IPage getPageList(SysProject sysProject);
}
