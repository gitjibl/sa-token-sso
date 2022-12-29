package com.example.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和部门关联表
 * @TableName sys_role_dept
 */
@TableName(value ="sys_role_dept")
@Data
public class SysRoleDept implements Serializable {
    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 部门ID
     */
    private Integer deptId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}