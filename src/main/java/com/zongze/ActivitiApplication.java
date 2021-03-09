package com.zongze;

import com.zongze.config.ApplicationContextHolder;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = {"com.zongze.mapper"})
public class ActivitiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ActivitiApplication.class, args);
        ApplicationContextHolder.setApplicationContext(configurableApplicationContext);
        Map<String, DataSource> dataSourceMap = configurableApplicationContext.getBeansOfType(DataSource.class);
        System.out.println(dataSourceMap);
    }

}
