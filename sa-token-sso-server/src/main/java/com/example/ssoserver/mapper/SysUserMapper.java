package com.example.ssoserver.mapper;

import com.example.ssoserver.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface SysUserMapper {
    @Select("select * from sys_user where username = #{username}")
    SysUser selectUser(@Param("username") String username);
}
