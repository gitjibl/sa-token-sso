package com.example.ssoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SaTokenSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenSsoServerApplication.class, args);
        System.out.println("\n------ 统一认证中心启动成功 ------ ");
    }

}
