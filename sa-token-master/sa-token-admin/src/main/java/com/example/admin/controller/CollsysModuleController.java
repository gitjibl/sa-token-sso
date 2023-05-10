package com.example.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.core.controller.BaseController;
import com.example.common.utils.R;
import com.example.system.domain.CollsysModule;
import com.example.system.service.CollsysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * collsys项目-模块管理
 *
 * @author huanyi
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/collsysmodule")
public class CollsysModuleController extends BaseController {

    @Autowired
    CollsysModuleService collsysModuleService;

    @SaCheckPermission("system:collsysmodule:list")
    @GetMapping("/getPageList")
    public R getPageList(CollsysModule collsysModule) {
        IPage pageList = collsysModuleService.getPageList(collsysModule);
        return R.ok(pageList);
    }

    @GetMapping("/getList")
    public R getList(CollsysModule collsysModule) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List<CollsysModule> list = collsysModuleService.list(queryWrapper);
        return R.ok(list);
    }

    @SaCheckPermission("system:collsysmodule:add")
    @GetMapping("/insert")
    public R insert(CollsysModule collsysModule) {
//        boolean update = collsysModuleService.save(collsysModule);
        boolean update = collsysModuleService.saveModule(collsysModule);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:collsysmodule:edit")
    @GetMapping("/update")
    public R update(CollsysModule collsysModule) {
        UpdateWrapper<CollsysModule> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", collsysModule.getId());
        boolean update = collsysModuleService.update(collsysModule, wrapper);
        return update ? R.ok() : R.fail();
    }

    @SaCheckPermission("system:collsysmodule:delete")
    @PostMapping("/delete")
    public R insert(@RequestBody String ids) {
        List<String> list = JSON.parseArray(ids, String.class);
        boolean update = collsysModuleService.removeByIds(list);
        return update ? R.ok() : R.fail();
    }


}
