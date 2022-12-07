package com.example.common.core.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author jibl
 */
@Data
public class LoginUser implements Serializable {
    /**
     * id，自增主键
     */
    private Integer userId;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 明文密码
     */
    private String pw;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 备注

     */
    private String remark;

    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    private static final long serialVersionUID = 1L;
}
