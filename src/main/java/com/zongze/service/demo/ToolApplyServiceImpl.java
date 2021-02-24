package com.zongze.service.demo;

import com.zongze.model.VariableHolder;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/2/24 16:28
 * @Created by xiezz
 */
@Service
public class ToolApplyServiceImpl extends AbstractActivitiService {
    public ToolApplyServiceImpl(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService, HistoryService historyService) {
        super(runtimeService, repositoryService, taskService, historyService, "myProcess_1");
    }

    @Override
    public void extraBusiness(Object model) {
        VariableHolder variableHolder = VariableHolder.newInstance().setProperties("remark", "回家").build();
        String id = startProcessInstance(variableHolder);
        String remark = (String) getProperties(id, "remark");
        System.out.println(remark);

    }
}
