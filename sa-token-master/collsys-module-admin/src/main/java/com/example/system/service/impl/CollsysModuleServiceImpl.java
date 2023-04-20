package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.domain.CollsysModule;
import com.example.system.mapper.CollsysModuleMapper;
import com.example.system.service.CollsysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author huanyi
 * @description 针对表【collsys_module】的数据库操作Service实现
 * @createDate 2023-04-19 13:42:39
 */
@Service
public class CollsysModuleServiceImpl extends ServiceImpl<CollsysModuleMapper, CollsysModule> implements CollsysModuleService {


    @Autowired
    CollsysModuleMapper collsysModuleMapper;

    @Override
    public IPage getPageList(CollsysModule collsysModule) {
        Page<CollsysModule> page = new Page<>(collsysModule.getPageNum(), collsysModule.getPageSize());
        QueryWrapper<CollsysModule> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(collsysModule.getName())) {
            queryWrapper.like("name", collsysModule.getName());
        }
        if (!ObjectUtils.isEmpty(collsysModule.getStatus())) {
            queryWrapper.eq("status", collsysModule.getStatus());
        }
        queryWrapper.orderByAsc("create_time");
        IPage<CollsysModule> list = collsysModuleMapper.selectPage(page, queryWrapper);
        return list;
    }
}




