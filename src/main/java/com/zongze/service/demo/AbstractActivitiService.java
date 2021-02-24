package com.zongze.service.demo;
import com.alibaba.fastjson.JSONObject;
import com.zongze.model.VariableHolder;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * @Date 2021/2/24 10:52
 * @Created by xiezz
 */
public abstract class AbstractActivitiService implements ActivitiService {

    private static final Logger log = LoggerFactory.getLogger(AbstractActivitiService.class);
    private static final String processInstanceId = "processInstanceId";
    private RuntimeService runtimeService;
    private RepositoryService repositoryService;
    private TaskService taskService;
    private HistoryService historyService;
    private String key;

    public AbstractActivitiService(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService, HistoryService historyService, String key) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.historyService = historyService;
        this.key = key;
    }

    @Override
    public void deployProcess(String bpmn) {
        //1.部署bpm文件
        Deployment deployment = repositoryService.createDeployment()
                .name("请假名称")
                .category("请假类")
                .addClasspathResource(bpmn)
                .deploy();
        log.info("流程部署成功，key={},id={},name={},category={}", deployment.getKey(), deployment.getId(), deployment.getName(), deployment.getCategory());
    }

    @Override
    public String startProcessInstance(VariableHolder variableHolder) {
        ProcessInstance processInstance= runtimeService.startProcessInstanceByKey(key);
        variableHolder.addProperties(processInstanceId, processInstance.getId());
        runtimeService.setVariables(processInstance.getId(), variableHolder.getVariables());
        extraBusiness(getActivitiModel(processInstance.getId()));
        return processInstance.getId();
    }

    @Override
    public void commitTask(String taskId) {

    }


    /**
     * 获取扩展的属性
     * @param processInstanceId 流程实例id
     * @param key 普通属性key
     * @return java.lang.Object
     */
    protected Object getProperties(String processInstanceId,String key){
        return runtimeService.getVariables(processInstanceId).get(key);
    }



    /**
     * 获取业务实体
     * @param processInstanceId 流程实例id
     * @return java.lang.Object 实体消息
     */
    protected Object getActivitiModel(String processInstanceId) {
        Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
        String jsonValue = (String) variables.get(VariableHolder.modelName);
        Class ClassType = null;
        try {
            ClassType = Class.forName((String) variables.get(VariableHolder.modelClass));
        } catch (ClassNotFoundException e) {
            log.error("类型未找到：{}",e);
        }
        return JSONObject.parseObject(jsonValue,ClassType);
    }


    /**
     * 业务处理
     */
    public abstract void extraBusiness(Object model);










}
