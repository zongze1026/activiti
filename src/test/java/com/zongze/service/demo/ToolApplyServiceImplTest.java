package com.zongze.service.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


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
    public void extraBusiness() {
        toolApplyService.extraBusiness(null);
    }
}