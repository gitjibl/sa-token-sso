package com.example.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.example.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName sys_project
 */
@TableName(value ="sys_project")
@Data
public class SysProject extends BaseEntity implements Serializable {
    /**
     * 项目ID
     */
    @TableId(value = "project_id")
    private String projectId;

    /**
     * 项目名称
     */
    @TableField(value = "project_name")
    private String projectName;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 地址
     */
    @TableField(value = "project_url")
    private String projectUrl;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}