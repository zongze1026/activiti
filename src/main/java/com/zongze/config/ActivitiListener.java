package com.zongze.config;

import com.zongze.model.ActivitiEntity;
import com.zongze.service.demo.AbstractActivitiService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.activiti.engine.delegate.event.ActivitiEventType.PROCESS_COMPLETED;
import static org.activiti.engine.delegate.event.ActivitiEventType.TASK_COMPLETED;
import static org.activiti.engine.delegate.event.ActivitiEventType.TASK_CREATED;


/**
 * @Date 2021/2/25 9:18
 * @Created by xiezz
 */
@Component
public class ActivitiListener implements ActivitiEventListener, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiListener.class);

    private final List<ActivitiEventType> events = Arrays.asList(TASK_CREATED, TASK_COMPLETED, PROCESS_COMPLETED);

    private ApplicationContext applicationContext;


    /**
     * 流程启动事件{@link org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl}
     * 以下其他的事件类型均为{@link org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl}
     */
//    @Override
//    public void onEvent(ActivitiEvent activitiEvent) {
//        if (events.stream().anyMatch(event -> event.equals(activitiEvent.getType()))) {
//            ActivitiEntityEventImpl entityEvent = (ActivitiEntityEventImpl) activitiEvent;
//            RuntimeService runtimeService = applicationContext.getBean(RuntimeService.class);
//            TaskService taskService = applicationContext.getBean(TaskService.class);
//            ActivitiEntity activitiEntity = new ActivitiEntity();
//            AbstractActivitiService service = null;
//            String className = (String) runtimeService.getVariables(activitiEvent.getProcessInstanceId()).get(ActivitiEntity.processClass);
//            try {
//                activitiEntity.setVariables(runtimeService.getVariables(activitiEvent.getProcessInstanceId()));
//                activitiEntity.setProcessInstanceId(activitiEvent.getProcessInstanceId());
//                if (entityEvent.getEntity() instanceof TaskEntityImpl) {
//                    TaskEntityImpl entity = (TaskEntityImpl) entityEvent.getEntity();
//                    activitiEntity.setRemark((String) taskService.getVariableLocal(entity.getId(), ActivitiEntity.remarkKey));
//                    activitiEntity.setTaskId(entity.getId());
//                    activitiEntity.setNodeRemark(entity.getName());
//                    activitiEntity.setRoleName(entity.getAssignee());
//                    Class aClass = Class.forName(className);
//                    service = (AbstractActivitiService) applicationContext.getBean(aClass);
//                }
//                service.dispatch(activitiEntity, activitiEvent);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }



    /**
     * 流程启动事件{@link org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl}
     * 以下其他的事件类型均为{@link ActivitiEntityEventImpl}
     */
    @SuppressWarnings("all")
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        switch (activitiEvent.getType()) {
            case PROCESS_STARTED:
                logger.info("流程开始事件触发，事件类型：{}", activitiEvent.getClass().getName());
                break;
            case TASK_COMPLETED:
                logger.info("任务完成事件促发，事件类型：{}", activitiEvent.getClass().getName());
                break;
            case PROCESS_COMPLETED:
                logger.info("流程结束事件触发，事件类型：{}", activitiEvent.getClass().getName());
                break;
            case TASK_CREATED:
                logger.info("任务创建事件促发，事件类型：{}", activitiEvent.getClass().getName());
                break;
        }

    }



    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
