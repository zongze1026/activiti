package com.zongze.service.demo;

import com.zongze.model.ProcessModel;
import com.zongze.model.ToosApply;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;


/**
 * @Date 2021/2/23 17:38
 * @Created by xiezz
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiServiceImplTest02 {

    @Autowired
    private ActivitiService02 activitiService;

    @Test
    public void deployProcess() {
        activitiService.deployProcess("processes/multi.bpmn");
    }

    @Test
    public void startProcessInstance() {
        ProcessModel processModel = new ProcessModel();
        ToosApply toosApply = new ToosApply();
        toosApply.setId(12);
        toosApply.setName("老王");
        toosApply.setCreateTime(new Date());
        processModel.setModel(toosApply);
        processModel.setKey("myProcess_1");
        activitiService.startProcessInstance(processModel);
        System.out.println(String.format("流程实例详情，id=%s",processModel.getProcessInstanceId()));
    }

    @Test
    public void commit() {
        activitiService.commit("260043");
    }


    @Test
    public void getTaskByProcessInstanceId() {
        ProcessModel processModel = new ProcessModel();
        processModel.setProcessInstanceId("15001");
        List<Task> tasks = activitiService.getTaskByProcessInstanceId(processModel);
        tasks.stream().forEach(task->{
            System.out.println(task.getId());
        });
    }


}