package com.example.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和角色关联表
 * @TableName sys_user_role
 */
@TableName(value ="sys_user_role")
@Data
public class SysUserRole implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}