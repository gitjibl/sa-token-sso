package com.example.ssoserver.mapper;

import com.example.ssoserver.domain.SysUser;
import org.apache.ibatis.annotations.*;


@Mapper
public interface SysUserMapper {
    @Select("select * from sys_user where username = #{username}")
    @Results(@Result(property = "userId", column = "user_id"))
    SysUser selectUser(@Param("username") String username);
}
