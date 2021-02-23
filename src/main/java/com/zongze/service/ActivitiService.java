package com.zongze.service;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @Date 2021/2/23 10:04
 * @Created by xiezz
 */
public interface ActivitiService {



    /**
     * 部署工作流
     * @param bpmn
     * @return void
     */
    void deployProcess(String bpmn);




    /**
     * 开启流程定义
     * @param key
     * @return org.activiti.engine.runtime.ProcessInstance
     */
    ProcessInstance startProcessInstance(String key,Map<String,Object> map);





    /**
     * 根据流程实例id查找任务
     * @param processInstanceId
     * @return java.util.List<org.activiti.engine.task.Task>
     */
    List<Task> getTaskByProcessInstanceId(String processInstanceId);














}
