package com.zongze.service.demo;

import com.zongze.model.ActivitiBusinessType;
import com.zongze.model.ActivitiEntity;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/3/9 14:18
 * @Created by xiezz
 */
@Service
public class KQApplyServiceImpl extends AbstractActivitiService {

    public KQApplyServiceImpl(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService, HistoryService historyService) {
        super(runtimeService, repositoryService, taskService, historyService, ActivitiBusinessType.LEAVE02);
    }


    @Override
    public void taskCreated(ActivitiEntity activitiEntity) {
        Object model = activitiEntity.getModel();

    }

    @Override
    public void taskCompleted(ActivitiEntity activitiEntity) {

    }

    @Override
    public void processCompleted(ActivitiEntity activitiEntity) {

    }

    @Override
    public String getDsNumberByRoleName(String roleName) {
        return null;
    }
}
