package com.example.demo.Hikari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhaobaozhi
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.demo.Hikari")
public class HikariApplication {

    public static void main(String[] args) {
        SpringApplication.run(HikariApplication.class, args);
    }
}
