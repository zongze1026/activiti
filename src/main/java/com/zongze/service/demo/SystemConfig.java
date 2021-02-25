package com.zongze.service.demo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/2/25 10:45
 * @Created by xiezz
 */
//@Getter
//@Component
//@Configuration
public class SystemConfig {

    @Value("${spring.activiti.datasource.url}")
    private String url;

    @Value("${spring.activiti.datasource.username}")
    private String username;

    @Value("${spring.activiti.datasource.password}")
    private String password;

    @Value("${spring.activiti.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.activiti.datasource.hikari.minimum-idle}")
    private Integer minimum;

    @Value("${spring.activiti.datasource.hikari.maximum-pool-size}")
    private Integer poolSize;










}
