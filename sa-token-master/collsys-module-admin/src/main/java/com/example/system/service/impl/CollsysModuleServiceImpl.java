package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.domain.CollsysModule;
import com.example.system.domain.dto.CollsysModuleCollDto;
import com.example.system.mapper.CollsysModuleMapper;
import com.example.system.service.CollsysModuleService;
import com.example.system.utils.RabbitMQConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author huanyi
 * @description 针对表【collsys_module】的数据库操作Service实现
 * @createDate 2023-04-19 13:42:39
 */
@Service
public class CollsysModuleServiceImpl extends ServiceImpl<CollsysModuleMapper, CollsysModule> implements CollsysModuleService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    CollsysModuleMapper collsysModuleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Gson gson = new GsonBuilder().serializeNulls().create();

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

    /**
     * 添加模块
     *
     * @param collsysModule
     * @return
     */
    @Override
    public boolean saveModule(CollsysModule collsysModule) {
        try {
            //通过 RabbitMQ 数据传输对象
            CollsysModuleCollDto collsysModuleCollDto = new CollsysModuleCollDto();
            BeanUtils.copyProperties(collsysModule, collsysModuleCollDto);
            //实体类示例转换成 json字符串格式报文
//            String message = gson.toJson(collsysModule);
            String message = gson.toJson(collsysModuleCollDto);

            //发送RabbitMQ 添加
            logger.info("当前发送时间：{}，发送一条消息给两个TTL队列：{}", new Date().toInstant(), message);
            rabbitTemplate.convertAndSend("", RabbitMQConstant.QUEUE_ADD, message);

            //判断返回 TODO 需对消息队列加入应答处理

            //存入数据库
            collsysModuleMapper.insert(collsysModule);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}




