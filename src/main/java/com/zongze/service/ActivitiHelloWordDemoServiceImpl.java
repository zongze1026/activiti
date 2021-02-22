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
        String deployMentKey = "请假审批";
        //1.部署bpm文件
        Deployment deployment = repositoryService.createDeployment()
                .key(deployMentKey)
                .name("请假名称")
                .category("请假类")
                .addClasspathResource("activiti.bpmn")
                .addClasspathResource("activiti.png")
                .deploy();
        log.info("流程部署成功，key=%s,id=%s,name=%s,category=%s", deployment.getKey(), deployment.getId(), deployment.getName(), deployment.getCategory());


        //2.获取流程定义
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
//        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().
        log.info("获取到的流程定义的数量：{}", list.size());
        list.stream().forEach(d -> {
            log.info("流程定义的id:{},流程定义的key:{}", d.getId(), d.getKey());
        });


        //3.开启流程实例
        Map<String,Object> mmap = new HashMap<>();
        mmap.put("role", "审批角色");
        mmap.put("bussinessKey", "业务key");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1","业务key",mmap);
        log.info("开启一个流程实例，id:{},流程实例名称：{},流程实例版本:{}", processInstance.getId(), processInstance.getName(), processInstance.getProcessDefinitionVersion());
        Map<String, Object> variables = processInstance.getProcessVariables();
        if (variables.size() > 0) {
            variables.entrySet().stream().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                System.out.println(key + "=" + value);
            });
        }


        //4.获取任务列表
//        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee("张三").list();
        List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey("业务key").list();

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
        Map<String, Object> processVariables = singleResult.getProcessVariables();
        if (processVariables.size() > 0) {
            processVariables.entrySet().stream().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                System.out.println(key + "=" + value);
            });
        }
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
