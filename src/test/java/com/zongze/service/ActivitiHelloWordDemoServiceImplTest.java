package com.zongze.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Date 2021/1/31 10:26
 * @Created by xiezz
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiHelloWordDemoServiceImplTest {

    @Autowired
    private ActivitiHelloWordDemoService activitiHelloWordDemoService;

    @Test
    public void startProcess() {
        activitiHelloWordDemoService.startProcess("myProcess_1");
    }
}