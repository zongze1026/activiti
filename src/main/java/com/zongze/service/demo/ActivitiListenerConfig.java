package com.zongze.service.demo;

import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2021/2/25 9:30
 * @Created by xiezz
 */
@Component
public class ActivitiListenerConfig implements ProcessEngineConfigurationConfigurer {


    @Autowired
    private ActivitiListener activitiListener;



    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        List<ActivitiEventListener> listeners = new ArrayList<>();
        listeners.add(activitiListener);
        springProcessEngineConfiguration.setEventListeners(listeners);
    }
}
