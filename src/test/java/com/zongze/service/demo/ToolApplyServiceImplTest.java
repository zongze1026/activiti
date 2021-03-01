package com.zongze.service.demo;
import com.zongze.model.ActivitiEntity;
import com.zongze.model.CommitLog;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;


/**
 * @Date 2021/2/24 16:33
 * @Created by xiezz
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolApplyServiceImplTest {

    @Autowired
    private ToolApplyServiceImpl toolApplyService;

    @Test
    public void startProcessInstance() {
        toolApplyService.deploy();
        //构建实体类
        CommitLog log = new CommitLog();
        log.setTaskId("14");
        log.setApplyTime(new Date());
        log.setName("张三");
        ActivitiEntity activitiEntity = ActivitiEntity.newBuilder()
                .setModel(log)
                .setProperties("hasRole", 1)
                .setProperties("moneryFlag", 1)
                .build();
        String processInstanceId = toolApplyService.openReviewProcess(activitiEntity);
        System.out.println(processInstanceId);

    }


    @Test
    public void commitTask() {
        toolApplyService.commitTask("212527", ActivitiEntity.ReviewFlag.REJECT,"不同意");
    }


    @Test
    public void queryTasks() {
        //查询任务
        List<Task> tasks = toolApplyService.getTasks("2505");
        tasks.stream().forEach(task -> System.out.println(task.getId()));
    }


}