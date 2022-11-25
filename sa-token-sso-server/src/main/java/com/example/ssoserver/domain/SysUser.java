package com.example.ssoserver.domain;

import lombok.Data;

@Data
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String pw;
    private String remark;
    private Integer sex;
    private String telephone;
    private Integer status;
}
