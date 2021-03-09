package com.zongze.service.demo;

import com.zongze.config.ApplicationContextHolder;
import com.zongze.model.ActivitiEntity;
import com.zongze.model.MoneyApply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Date 2021/3/9 15:35
 * @Created by xiezz
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KQApplyServiceImplTest implements ApplicationContextAware {

    @Autowired
    private KQApplyServiceImpl kqApplyService;

    @Test
    public void CommitTask(){
        MoneyApply moneyApply = new MoneyApply();
        moneyApply.setAmount("2500");
        moneyApply.setDsNumber("tfv2386173198918");
        moneyApply.setEngineeringChildGroup(118);
        moneyApply.setEngineeringGroupId(201);
        moneyApply.setName("张三");
        kqApplyService.commitTask(moneyApply);
    }


    @Test
    public void taskCompleted() {
        kqApplyService.commitTask("55006", ActivitiEntity.ReviewFlag.AGREE, "金额太大需要老板审批");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.setApplicationContext(applicationContext);
    }

}