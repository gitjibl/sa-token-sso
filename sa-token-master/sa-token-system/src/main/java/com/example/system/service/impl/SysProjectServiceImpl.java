package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.domain.SysProject;
import com.example.system.mapper.SysProjectMapper;
import com.example.system.service.SysProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author jibl
 * @description 针对表【sys_project】的数据库操作Service实现
 * @createDate 2022-11-28 13:46:06
 */
@Service
public class SysProjectServiceImpl extends ServiceImpl<SysProjectMapper, SysProject> implements SysProjectService {


    @Autowired
    SysProjectMapper sysProjectMapper;

    @Override
    public IPage getPageList(SysProject sysProject) {
        Page<SysProject> page = new Page<>(sysProject.getPageNum(), sysProject.getPageSize());
        QueryWrapper<SysProject> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(sysProject.getProjectName())) {
            queryWrapper.like("project_name", sysProject.getProjectName());
        }
        if (!ObjectUtils.isEmpty(sysProject.getStatus())) {
            queryWrapper.eq("status", sysProject.getStatus());
        }
        queryWrapper.orderByAsc("create_time");
        IPage<SysProject> list = sysProjectMapper.selectPage(page, queryWrapper);
        return list;
    }
}




