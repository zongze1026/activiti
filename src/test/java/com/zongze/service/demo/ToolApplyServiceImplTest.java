package com.zongze.service.demo;
import com.alibaba.fastjson.JSON;
import com.zongze.config.ApplicationContextHolder;
import com.zongze.model.ActivitiEntity;
import com.zongze.model.MoneyApply;
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
import java.util.List;



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
        //构建实体类
        MoneyApply moneyApply = new MoneyApply();
        moneyApply.setAmount("2500");
        moneyApply.setDsNumber("tfv2386173198918");
        moneyApply.setEngineeringChildGroup(118);
        moneyApply.setEngineeringGroupId(201);
        moneyApply.setName("张三");
        List<String> userList = new ArrayList<>();
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        userList.add("tfa2386173198918");
        toolApplyService.commitTask(moneyApply);
        ActivitiEntity activitiEntity = ActivitiEntity.newBuilder().setModel(moneyApply).build();
        String processInstanceId = toolApplyService.startMultiTask(activitiEntity, userList, 0.2,false);
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
//        List<String> list = toolApplyService.getTasks("60001").stream().map(task->task.getId()).collect(Collectors.toList());
//        for (int i = 0; i < list.size(); i++) {
//            if(i % 2 ==0){
//                toolApplyService.commitTask(list.get(i), ActivitiEntity.ReviewFlag.AGREE, "上面有人"+i);
//            }else{
//                toolApplyService.commitTask(list.get(i), ActivitiEntity.ReviewFlag.REJECT, "没有关系"+i);
//            }
//        }

        toolApplyService.commitTask("62520", ActivitiEntity.ReviewFlag.AGREE, "同意申请");
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