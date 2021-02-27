package com.zongze.service.demo;

import com.zongze.model.ActivitiBusinessType;
import com.zongze.model.ActivitiEntity;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Date 2021/2/24 10:52
 * @Created by xiezz
 */
public abstract class AbstractActivitiService implements ActivitiService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractActivitiService.class);
    private RuntimeService runtimeService;
    private RepositoryService repositoryService;
    private TaskService taskService;
    private HistoryService historyService;
    private ActivitiBusinessType activitiBusinessType;

    public AbstractActivitiService(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService,
                                   HistoryService historyService, ActivitiBusinessType activitiBusinessType) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.historyService = historyService;
        this.activitiBusinessType = activitiBusinessType;
    }

    @Override
    public Deployment deploy() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(activitiBusinessType.getFileName())
                .deploy();
        logger.info("流程部署成功,流程id：{}", deployment.getId());
        return deployment;
    }


    @Override
    public Deployment deploy(String processKey) {
        ActivitiBusinessType businessType = ActivitiBusinessType.getBusinessType(processKey);
        Assert.notNull(businessType, "bpmn文件未找到");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(businessType.getFileName())
                .deploy();
        logger.info("流程部署成功,流程id：{}", deployment.getId());
        return deployment;
    }


    @Override
    public void commitTask(String taskId, ActivitiEntity.ReviewFlag reviewFlag, String remark) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!StringUtils.isEmpty(remark)) {
            taskService.setVariableLocal(taskId, ActivitiEntity.remarkKey, remark);
        }
        if (reviewFlag.equals(ActivitiEntity.ReviewFlag.REJECT)) {
            setReviewFlag(task.getProcessInstanceId(), reviewFlag);
        }
        taskService.complete(taskId);
    }


    @Override
    public List<Task> getTasks(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }


    @Override
    public String openReviewProcess(ActivitiEntity activitiEntity) {
        String processInstanceId = startProcessInstance(activitiEntity);
        Task firstTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        taskService.complete(firstTask.getId());
        return processInstanceId;
    }

    @Override
    public void dispatch(ActivitiEntity activitiEntity, ActivitiEvent activitiEvent) {
        switch (activitiEvent.getType()) {
            case PROCESS_STARTED:
                logger.info("流程开始事件触发，事件类型：{}", activitiEvent.getClass().getName());
                break;
            case TASK_COMPLETED:
                taskCompleted(activitiEntity);
                break;
            case PROCESS_COMPLETED:
                processCompleted(activitiEntity);
                break;
            case TASK_CREATED:
                taskCreated(activitiEntity);
                processReviewTypeAfterTaskCreated(activitiEntity);
                break;
        }
    }


    /**
     * 处理审批类型
     *
     * @param activitiEntity
     */
    private void processReviewTypeAfterTaskCreated(ActivitiEntity activitiEntity) {
        ActivitiEntity.ReviewType reviewType = activitiEntity.getReviewType();
        if (reviewType.equals(ActivitiEntity.ReviewType.APPLY)) {
            runtimeService.setVariable(activitiEntity.getProcessInstanceId(), ActivitiEntity.reviewTypeKey, ActivitiEntity.ReviewType.REVIEW);
        }
    }


    /**
     * 设置审批状态
     *
     * @param processInstanceId
     * @param reviewFlag
     */
    private void setReviewFlag(String processInstanceId, ActivitiEntity.ReviewFlag reviewFlag) {
        Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
        variables.put(ActivitiEntity.reviewFlagKey, reviewFlag.getFlag());
        runtimeService.setVariables(processInstanceId, variables);
    }


    /**
     * 开启流程实例
     *
     * @param activitiEntity
     */
    public String startProcessInstance(ActivitiEntity activitiEntity) {
        activitiEntity.setProperties(ActivitiEntity.processClass, this.getClass().getName());
        return runtimeService.startProcessInstanceByKey(activitiBusinessType.getProcessKey(), activitiEntity.getVariables()).getId();
    }

}
