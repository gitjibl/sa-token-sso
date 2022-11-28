package com.example.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(value = {"com.example.**.mapper"})
@ComponentScan({"com.example.*"})
public class SaTokenAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenAdminApplication.class, args);
    }

}
