package com.zongze.service.demo;

import com.zongze.model.ProcessModel;
import org.activiti.engine.task.Task;
import java.util.List;

/**
 * @Date 2021/2/23 17:06
 * @Created by xiezz
 */
public interface ActivitiService02 {


    /**
     * 部署工作流
     * @param bpmn
     * @return void
     */
    void deployProcess(String bpmn);




    /**
     * 开启流程定义
     * @param processModel
     * @return org.activiti.engine.runtime.ProcessInstance
     */
    ProcessModel startProcessInstance(ProcessModel processModel);





    /**
     * 根据流程实例id查找任务
     * @param processModel
     * @return java.util.List<org.activiti.engine.task.Task>
     */
    List<Task> getTaskByProcessInstanceId(ProcessModel processModel);




    /**
     * 提交任务
     * @param taskId
     * @return java.util.List<org.activiti.engine.task.Task>
     */
    ProcessModel commit(String taskId);














}
