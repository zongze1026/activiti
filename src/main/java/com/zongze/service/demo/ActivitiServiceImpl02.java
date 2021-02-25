package com.zongze.service.demo;

import com.zongze.model.CommitLog;
import com.zongze.model.ProcessModel;
import com.zongze.model.ToosApply;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2021/2/23 17:11
 * @Created by xiezz
 */
@Service
public class ActivitiServiceImpl02 implements ActivitiService02 {

    private static final Logger log = LoggerFactory.getLogger(ActivitiServiceImpl02.class);
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
    public ProcessModel startProcessInstance(ProcessModel processModel) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("model", processModel);
        variables.put("flag", 2);
        ProcessInstance processInstance= runtimeService.startProcessInstanceByKey(processModel.getKey());
        processModel.setProcessInstanceId(processInstance.getId());
        runtimeService.setVariables(processModel.getProcessInstanceId(), variables);
//        runner(processModel);
        return processModel;
    }

    @Override
    public List<Task> getTaskByProcessInstanceId(ProcessModel processModel) {
        Map<String, Object> variables = runtimeService.getVariables(processModel.getProcessInstanceId());
        variables.entrySet().stream().forEach(entry->{
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key+"="+value.toString());
        });
        return taskService.createTaskQuery().processInstanceId(processModel.getProcessInstanceId()).list();
    }


    @Override
    public ProcessModel commit(String taskId) {
        taskService.complete(taskId);
        return null;
    }



    private void runner(ProcessModel processModel){
        ToosApply toosApply = (ToosApply) processModel.getModel();
        List<Task> tasks = getTaskByProcessInstanceId(processModel);
        tasks.stream().forEach(task->{
            CommitLog log = new CommitLog();
            log.setApplyId(toosApply.getId());
            log.setName(task.getName());
            log.setTaskId(task.getId());
            log.setApplyTime(toosApply.getCreateTime());
            log.setCreateTime(new Date());
            System.out.println(log);
        });
    }



}
