package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.domain.SysDept;
import com.example.admin.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统部门管理
 *
 * @author huanyi
 * @date 2022-11-29
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    SysDeptService sysDeptService;

    /**
     * 查询部分列表
     *
     * @param sysDept
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public IPage getList(SysDept sysDept) {
        return sysDeptService.getPageList(sysDept);
    }
}
