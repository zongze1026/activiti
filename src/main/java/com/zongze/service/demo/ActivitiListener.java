package com.zongze.service.demo;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Date 2021/2/25 9:18
 * @Created by xiezz
 */
@Component
public class ActivitiListener implements ActivitiEventListener {

    private final Logger logger = LoggerFactory.getLogger(ActivitiListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()){
            case PROCESS_STARTED:
                logger.info("流程开始事件触发,流程id：{},流程执行id：{}", event.getProcessInstanceId(),event.getExecutionId());
                break;
            case TASK_COMPLETED:
                logger.info("任务完成事件触发,流程id：{},流程执行id：{}", event.getProcessInstanceId(),event.getExecutionId());
                break;
            case PROCESS_COMPLETED:
                logger.info("流程结束事件触发,流程id：{},流程执行id：{}", event.getProcessInstanceId(),event.getExecutionId());
                break;
            case TASK_CREATED:
                logger.info("任务创建事件触发,流程id：{},流程执行id：{}", event.getProcessInstanceId(),event.getExecutionId());
                break;
        }

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
