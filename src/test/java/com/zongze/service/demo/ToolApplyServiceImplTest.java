package com.zongze.service.demo;
import com.alibaba.fastjson.JSON;
import com.zongze.config.ApplicationContextHolder;
import com.zongze.model.ActivitiEntity;
import com.zongze.model.CommitLog;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Date 2021/2/24 16:33
 * @Created by xiezz
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolApplyServiceImplTest implements ApplicationContextAware {

    @Autowired
    private ToolApplyServiceImpl toolApplyService;

    @Test
    public void startProcessInstance() {
//        toolApplyService.deploy();
        //构建实体类
        CommitLog log = new CommitLog();
        log.setTaskId("14");
        log.setApplyTime(new Date());
        log.setName("张三");
        List<String> userList = new ArrayList<>();
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        ActivitiEntity activitiEntity = ActivitiEntity.newBuilder().setModel(log).build();
        String processInstanceId = toolApplyService.startMultiTask(activitiEntity, userList, 0.0,false);
//        String processInstanceId = toolApplyService.startGeneralTask(activitiEntity);
        System.out.println(processInstanceId);
    }


    @Test
    public void findDeployment(){
        Deployment deploymentInstance = toolApplyService.findDeploymentInstance();
        if(deploymentInstance != null){
            System.out.println(JSON.toJSONString(deploymentInstance));
        }else{
            System.out.println("没有查询到部署实例");
        }
    }




    @Test
    public void commitTask() {
        List<String> list = toolApplyService.getTasks("2501").stream().map(task->task.getId()).collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            if(i % 2 ==0){
                toolApplyService.commitTask(list.get(i), ActivitiEntity.ReviewFlag.AGREE, "上面有人"+i);
            }else{
                toolApplyService.commitTask(list.get(i), ActivitiEntity.ReviewFlag.REJECT, "没有关系"+i);
            }
        }

//        toolApplyService.commitTask("172595", ActivitiEntity.ReviewFlag.AGREE, "上面有人");
    }


    @Test
    public void queryTasks() {
        //查询任务
        List<Task> tasks = toolApplyService.getTasks("220005");
        tasks.stream().forEach(task -> System.out.println(task.getId()));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.setApplicationContext(applicationContext);
    }
}