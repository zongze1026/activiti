package com.zongze.config;

import com.zongze.model.ActivitiEntity;
import com.zongze.service.demo.AbstractActivitiService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/3/1 15:21
 * @Created by xiezz
 */
@Component
public class MultiTaskListener implements TaskListener {
    private static final Logger logger = LoggerFactory.getLogger(MultiTaskListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("The Multi task node delete,processInstanceId:{},taskId:{}", delegateTask.getProcessInstanceId(), delegateTask.getId());
        try {
            RuntimeService runtimeService = ApplicationContextHolder.getBean(RuntimeService.class);
            ActivitiEntity activitiEntity = new ActivitiEntity();
            activitiEntity.setProcessInstanceId(delegateTask.getProcessInstanceId());
            activitiEntity.setVariables(runtimeService.getVariables(delegateTask.getProcessInstanceId()));
            String className = (String) activitiEntity.getProperties(ActivitiEntity.getProcessClass());
            Class aClass = Class.forName(className);
            AbstractActivitiService service = (AbstractActivitiService) ApplicationContextHolder.getBean(aClass);
            service.taskNodeDelete(activitiEntity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
