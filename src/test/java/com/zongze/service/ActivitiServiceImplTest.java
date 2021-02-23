package com.zongze.service;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2021/2/23 10:13
 * @Created by xiezz
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiServiceImplTest {

    @Autowired
    private ActivitiService activitiService;


    @Test
    public void deployProcess(){
        activitiService.deployProcess("containGateway.bpmn");
    }


    @Test
    public void startProcessInstance(){
        Map<String,Object> map = new HashMap<>();
        map.put("age", 100);
        ProcessInstance processInstance = activitiService.startProcessInstance("myProcess_1",map);
        System.out.println(String.format("流程实例详情，id=%s",processInstance.getId()));
    }


    @Test
    public void getTaskByProcessInstanceId(){
        List<Task> task = activitiService.getTaskByProcessInstanceId("870af18f-758a-11eb-900b-18c04d28dcf0");
        task.stream().forEach(t->System.out.println(t.getName()));
    }











}