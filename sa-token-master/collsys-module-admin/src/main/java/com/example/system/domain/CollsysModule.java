package com.example.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName collsys_module
 */
@TableName(value = "collsys_module")
@Data
public class CollsysModule extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 网站名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 连接地址
     */
    @TableField(value = "link")
    private String link;

    /**
     * 类别 1：工具
     */
    @TableField(value = "category")
    private Integer category;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
//    @TableField(value = "create_time")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
//    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
