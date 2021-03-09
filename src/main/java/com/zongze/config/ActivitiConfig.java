package com.zongze.config;

import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2021/2/25 14:38
 * @Created by xiezz
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration implements ProcessEngineConfigurationConfigurer {


    @Autowired
    private ActivitiListener activitiListener;


    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.activiti.datasource")
    public DataSource activitiDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public PlatformTransactionManager activitiTransactionManager(){
        return new DataSourceTransactionManager(activitiDataSource());
    }


    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(SpringAsyncExecutor springAsyncExecutor) throws IOException {

        return baseSpringProcessEngineConfiguration(
                activitiDataSource(),
                activitiTransactionManager(),
                springAsyncExecutor);
    }


    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        List<ActivitiEventListener> listeners = new ArrayList<>();
        listeners.add(activitiListener);
        processEngineConfiguration.setEventListeners(listeners);
    }
}
