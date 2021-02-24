package com.zongze.service.demo;

import com.zongze.model.VariableHolder;

/**
 * @Date 2021/2/24 10:47
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
     * 开启流程实例
     * @param variableHolder
     * @return java.lang.String
     */
    String startProcessInstance(VariableHolder variableHolder);




    /**
     * 提交任务
     * @param taskId
     */
    void commitTask(String taskId);













}
