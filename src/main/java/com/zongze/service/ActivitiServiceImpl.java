package com.zongze.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @Date 2021/2/23 10:09
 * @Created by xiezz
 */
@Service
public class ActivitiServiceImpl implements ActivitiService {

    private static final Logger log = LoggerFactory.getLogger(ActivitiHelloWordDemoServiceImpl.class);
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

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
    public ProcessInstance startProcessInstance(String key, Map<String,Object> map) {
        if(CollectionUtils.isEmpty(map)){
            return runtimeService.startProcessInstanceByKey(key);
        }
        return runtimeService.startProcessInstanceByKey(key,map);
    }


    @Override
    public List<Task> getTaskByProcessInstanceId(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }
}
