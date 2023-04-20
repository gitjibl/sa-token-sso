package com.example.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.system.domain.CollsysModule;

/**
 * @author huanyi
 * @description 针对表【collsys_module】的数据库操作Service
 * @createDate 2023-04-19 13:42:39
 */
public interface CollsysModuleService extends IService<CollsysModule> {

    IPage getPageList(CollsysModule collsysModule);


}
