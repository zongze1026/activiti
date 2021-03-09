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
public class ActivitiListener implements ActivitiEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ActivitiListener.class);

    private final List<ActivitiEventType> events = Arrays.asList(TASK_CREATED, TASK_COMPLETED, PROCESS_COMPLETED);


    /**
     * 流程启动事件{@link org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl}
     * 以下其他的事件类型均为{@link ActivitiEntityEventImpl}
     */
    @SuppressWarnings("all")
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        if (events.stream().anyMatch(event -> event.equals(activitiEvent.getType()))) {
            try {
                RuntimeService runtimeService = ApplicationContextHolder.getBean(RuntimeService.class);
                TaskService taskService = ApplicationContextHolder.getBean(TaskService.class);
                ActivitiEntity activitiEntity = new ActivitiEntity();
                activitiEntity.setProcessInstanceId(activitiEvent.getProcessInstanceId());
                activitiEntity.setVariables(runtimeService.getVariables(activitiEvent.getProcessInstanceId()));
                activitiEntity.setReviewType(ActivitiEntity.ReviewType.getReviewTypeInfo((Integer) activitiEntity.getProperties(ActivitiEntity.getReviewTypeKey())));
                ActivitiEntityEventImpl entityEvent = (ActivitiEntityEventImpl) activitiEvent;
                String className = (String) activitiEntity.getProperties(ActivitiEntity.getProcessClass());
                Class aClass = Class.forName(className);
                AbstractActivitiService service = (AbstractActivitiService) ApplicationContextHolder.getBean(aClass);
                TaskEntityImpl entity;
                switch (activitiEvent.getType()) {
                    case TASK_COMPLETED:
                        entity = (TaskEntityImpl) entityEvent.getEntity();
                        activitiEntity.setCommitRemark((String) activitiEntity.getProperties(ActivitiEntity.getRemarkKey()));
                        activitiEntity.setNodeRemark(entity.getName());
                        activitiEntity.setRoleName(entity.getAssignee());
                        activitiEntity.setTaskId(entity.getId());
                        activitiEntity.setReviewFlag(ActivitiEntity.ReviewFlag.getReviewFlagInfo((Integer) activitiEntity.getProperties(ActivitiEntity.getReviewFlagKey())));
                        break;
                    case TASK_CREATED:
                        entity = (TaskEntityImpl) entityEvent.getEntity();
                        activitiEntity.setNodeRemark(entity.getName());
                        activitiEntity.setRoleName(entity.getAssignee());
                        activitiEntity.setTaskId(entity.getId());
                        break;
                    case PROCESS_COMPLETED:
                        activitiEntity.setReviewFlag(ActivitiEntity.ReviewFlag.getReviewFlagInfo((Integer) activitiEntity.getProperties(ActivitiEntity.getReviewFlagKey())));
                        break;
                }
                service.dispatch(activitiEntity, activitiEvent);
            } catch (ClassNotFoundException e) {
                logger.error("activiti grolabl listener error {}", e);
            }
        }
    }


    @Override
    public boolean isFailOnException() {
        return false;
    }

}
