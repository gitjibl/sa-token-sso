package com.example.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.example.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser extends BaseEntity implements Serializable {
    /**
     * id，自增主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    private Integer deptId;

    /**
     * 用户账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 明文密码
     */
    @TableField(value = "pw")
    private String pw;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 电话
     */
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 备注

     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 帐号状态（0正常 1停用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /** 角色ID */
    private Long roleId;

    /** 角色对象 */
    @TableField(exist = false)
    private List<SysRole> roles;

    /** 角色组 */
    @TableField(exist = false)
    private Integer[] roleIds;

    public static boolean isAdmin(Integer userId)
    {
        return userId != null && 1 == userId;
    }
}