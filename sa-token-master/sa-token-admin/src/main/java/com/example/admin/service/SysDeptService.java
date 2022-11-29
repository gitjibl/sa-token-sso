package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jibl
* @description 针对表【sys_dept(部门表)】的数据库操作Service
* @createDate 2022-11-28 11:35:31
*/
public interface SysDeptService extends IService<SysDept> {

    IPage getPageList(SysDept sysDept);
}
