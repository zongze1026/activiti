package com.zongze.config;

import com.zongze.service.demo.ActivitiService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Date 2021/3/9 13:51
 * @Created by xiezz
 */
@Component
public class ActivitiProcessDeployCommandLineRunnerImpl implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        Map<String, ActivitiService> activitiServiceMap = applicationContext.getBeansOfType(ActivitiService.class);
        System.out.println(activitiServiceMap.size());
        if(!CollectionUtils.isEmpty(activitiServiceMap)){
            activitiServiceMap.entrySet().forEach(entry->{
                ActivitiService activitiService = entry.getValue();
                Deployment deploymentInstance = activitiService.findDeploymentInstance();
                if(null == deploymentInstance){
                    activitiService.deploy();
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
