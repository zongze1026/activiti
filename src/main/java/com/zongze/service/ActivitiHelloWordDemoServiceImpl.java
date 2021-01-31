package com.zongze.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2021/1/31 10:23
 * @Created by xiezz
 */
@Service
public class ActivitiHelloWordDemoServiceImpl implements ActivitiHelloWordDemoService {

    private static final Logger log = LoggerFactory.getLogger(ActivitiHelloWordDemoServiceImpl.class);
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;


    @Override
    public void startProcess(String processKey) {
        //1.部署bpm文件
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("activiti.bpmn")
                .addClasspathResource("activiti.png")
                .deploy();


        //2.获取流程定义
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        log.info("获取到的流程定义的数量：{}", list.size());
        list.stream().forEach(d -> {
            log.info("流程定义的id:{},流程定义的key:{}", d.getId(), d.getKey());
        });


        //3.开启流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(list.get(0).getKey());
        log.info("开启一个流程实例，id:{},流程实例名称：{}", processInstance.getId(), processInstance.getName());


        //4.获取任务列表
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("张三").list();
        log.info("获取的任务数量：{}", tasks.size());
        tasks.stream().forEach(task -> {
            log.info("任务id:{},任务名称：{}", task.getId(), task.getName());
        });


        //5.发起考勤申请
        Map<String, Object> map;
        Task task = tasks.get(0);
        map = new HashMap<>();
        map.put("date", new Date());
        map.put("remark", "漏打卡提交");
        taskService.complete(task.getId(), map);


        //6.现场负责人审批;审批流程和第五步考勤提交一样
        Task singleResult = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("李四").singleResult();
        map = new HashMap<>();
        map.put("date", new Date());
        map.put("remark", "现场负责人审批同意");
        taskService.complete(singleResult.getId(), map);


        //7.现场负责人审批;审批流程和第五步考勤提交一样
        Task singleTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("王五").singleResult();
        map = new HashMap<>();
        map.put("date", new Date());
        map.put("remark", "经济责任人审批同意");
        taskService.complete(singleTask.getId(), map);

    }


}
