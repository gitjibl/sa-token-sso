package com.example.ssoclient001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SsoClient001Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoClient001Application.class, args);
        System.out.println("\nSa-Token SSO模式二 Client端启动成功");
    }

}
