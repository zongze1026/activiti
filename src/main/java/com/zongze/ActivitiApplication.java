package com.zongze;

import com.zongze.config.ApplicationContextHolder;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ActivitiApplication.class, args);
        ApplicationContextHolder.setApplicationContext(configurableApplicationContext);
    }

}
