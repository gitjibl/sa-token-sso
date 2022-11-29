package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.domain.SysDept;
import com.example.admin.mapper.SysDeptMapper;
import com.example.admin.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jibl
 * @description 针对表【sys_dept(部门表)】的数据库操作Service实现
 * @createDate 2022-11-28 11:35:31
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept>
        implements SysDeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    @Override
    public IPage getPageList(SysDept sysDept) {
        Map<String, Object> map = new HashMap<>();
//        Page<SysDept> page = new Page<>(sysDept.getPageNum(), sysDept.getPageSize());
        Page<SysDept> page = new Page<>();
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        IPage<SysDept> list = sysDeptMapper.selectPage(page, queryWrapper);
        return list;
    }
}




