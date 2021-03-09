package com.zongze.service.demo;

import com.alibaba.fastjson.JSON;
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
import org.springframework.util.ObjectUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * @Date 2021/2/24 10:52
 * @Created by xiezz
 */
public abstract class AbstractActivitiService implements ActivitiService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractActivitiService.class);
    private final String dsNumberRegex = "^[a-zA-Z]{3}\\d{13}$";
    public RuntimeService runtimeService;
    public RepositoryService repositoryService;
    public TaskService taskService;
    public HistoryService historyService;
    private ActivitiBusinessType activitiBusinessType;
    public final Lock lock = new ReentrantLock();

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
                .key(activitiBusinessType.getProcessKey())
                .addClasspathResource(activitiBusinessType.getFileName())
                .deploy();
        logger.info("process deploy success ,deployId：{}", deployment.getId());
        return deployment;
    }


    @Override
    public Deployment deploy(String processKey) {
        ActivitiBusinessType businessType = ActivitiBusinessType.getBusinessType(processKey);
        Assert.notNull(businessType, "The bpmn file not find");
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(businessType.getFileName())
                .deploy();
        logger.info("process deploy success ,deployId：{}", deployment.getId());
        return deployment;
    }


    @Override
    public Deployment findDeploymentInstance() {
        return repositoryService.createDeploymentQuery().deploymentKey(activitiBusinessType.getProcessKey()).singleResult();
    }

    @Override
    public void commitTask(Task task, ActivitiEntity.ReviewFlag reviewFlag, String remark) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(ActivitiEntity.getReviewFlagKey(), reviewFlag.getFlag());
        variables.put(ActivitiEntity.getRemarkKey(), remark);
        taskService.complete(task.getId(), variables);
    }


    @Override
    public void commitTask(String taskId, ActivitiEntity.ReviewFlag reviewFlag, String remark) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!ObjectUtils.isEmpty(task)) {
            commitTask(task, reviewFlag, remark);
        }
    }

    @Override
    public List<Task> getTasks(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }


    @Override
    public String startGeneralTask(ActivitiEntity activitiEntity) {
        String processInstanceId = startProcessInstance(activitiEntity);
        Task firstTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        taskService.complete(firstTask.getId());
        return processInstanceId;
    }

    @Override
    public void dispatch(ActivitiEntity activitiEntity, ActivitiEvent activitiEvent) {
        switch (activitiEvent.getType()) {
            case TASK_COMPLETED:
                logger.info("任务完成触发，流程实体：{}", JSON.toJSONString(activitiEntity));
                taskCompleted(activitiEntity);
                break;
            case PROCESS_COMPLETED:
                logger.info("审批流程结束，流程实体：{}", JSON.toJSONString(activitiEntity));
                processCompleted(activitiEntity);
                break;
            case TASK_CREATED:
                logger.info("任务创建触发，流程实体：{}", JSON.toJSONString(activitiEntity));
                processReviewTypeBeforeTaskCreated(activitiEntity);
                taskCreated(activitiEntity);
                processReviewTypeAfterTaskCreated(activitiEntity);
                break;
        }
    }


    /**
     * 得盛号处理
     *
     * @param activitiEntity
     * @return void
     */
    protected void processReviewTypeBeforeTaskCreated(ActivitiEntity activitiEntity) {
        ActivitiEntity.ReviewType reviewType = activitiEntity.getReviewType();
        if (reviewType.equals(ActivitiEntity.ReviewType.REVIEW)) {
            if (!determineDsNumber(activitiEntity.getRoleName())) {
                activitiEntity.setDsNumber(getDsNumberByRoleName(activitiEntity.getRoleName()));
            }
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
            setProperties(activitiEntity.getProcessInstanceId(), ActivitiEntity.getReviewTypeKey(), ActivitiEntity.ReviewType.REVIEW.getFlag());
        }
    }


    /**
     * 开启流程实例
     *
     * @param activitiEntity
     */
    public String startProcessInstance(ActivitiEntity activitiEntity) {
        activitiEntity.getVariables().put(ActivitiEntity.getProcessClass(), this.getClass().getName());
        return runtimeService.startProcessInstanceByKey(activitiBusinessType.getProcessKey(), activitiEntity.getVariables()).getId();
    }


    /**
     * 判断是否是得盛号
     *
     * @param dsNumber
     * @return boolean
     */
    public boolean determineDsNumber(String dsNumber) {
        Assert.hasText(dsNumber, "得盛号不为空");
        return Pattern.matches(dsNumberRegex, dsNumber);
    }


    /**
     * 获取变量属性
     *
     * @param activitiEntity
     * @param key
     * @return java.lang.Object
     */
    protected Object getProperties(ActivitiEntity activitiEntity, String key) {
        return activitiEntity.getVariables().get(key);
    }


    /**
     * 获取变量属性
     *
     * @param task
     * @param key
     * @return java.lang.Object
     */
    protected Object getProperties(Task task, String key) {
        return runtimeService.getVariable(task.getProcessInstanceId(), key);
    }


    /**
     * 获取变量属性
     *
     * @param task
     * @param key
     * @return java.lang.Object
     */
    protected Integer getIntProperties(Task task, String key) {
        return (Integer) runtimeService.getVariable(task.getProcessInstanceId(), key);
    }


    /**
     * 获取变量属性
     *
     * @param task
     * @param key
     * @return java.lang.Object
     */
    protected Boolean getBooleanProperties(Task task, String key) {
        return (Boolean) runtimeService.getVariable(task.getProcessInstanceId(), key);
    }


    /**
     * 获取变量属性
     *
     * @param task
     * @param key
     * @return java.lang.Object
     */
    protected Double getDoubleProperties(Task task, String key) {
        return (Double) runtimeService.getVariable(task.getProcessInstanceId(), key);
    }


    /**
     * 获取变量属性
     *
     * @param task
     * @param key
     * @return java.lang.Object
     */
    protected String getStringProperties(Task task, String key) {
        return (String) runtimeService.getVariable(task.getProcessInstanceId(), key);
    }


    /**
     * 设置属性变量
     *
     * @param task
     * @param key
     * @param value
     * @return void
     */
    protected void setProperties(Task task, String key, Object value) {
        setProperties(task.getProcessInstanceId(), key, value);
    }


    /**
     * 设置属性变量
     *
     * @param processInstanceId
     * @param key
     * @param value
     * @return void
     */
    protected void setProperties(String processInstanceId, String key, Object value) {
        runtimeService.setVariable(processInstanceId, key, value);
    }


}
