package com.zongze.service.demo;
import com.alibaba.fastjson.JSON;
import com.zongze.model.ActivitiBusinessType;
import com.zongze.model.ActivitiEntity;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Date 2021/2/24 16:28
 * @Created by xiezz
 */
@Service
public class ToolApplyServiceImpl extends AbstractMultiProcessActivitiService {

    private final Logger logger = LoggerFactory.getLogger(ToolApplyServiceImpl.class);

    public ToolApplyServiceImpl(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService, HistoryService historyService) {
        super(runtimeService, repositoryService, taskService, historyService, ActivitiBusinessType.LEAVE);
    }

    @Override
    public void taskCreated(ActivitiEntity activitiEntity) {
        logger.info("任务创建：{}", JSON.toJSONString(activitiEntity));
    }

    @Override
    public void taskCompleted(ActivitiEntity activitiEntity) {
        if (activitiEntity.getReviewFlag().equals(ActivitiEntity.ReviewFlag.AGREE)) {
            logger.info("任务完成：{}，参数：{}", "审批同意", JSON.toJSON(activitiEntity));
        } else {
            logger.info("任务完成：{},参数：{}", "审批拒绝", JSON.toJSON(activitiEntity));
        }
    }

    @Override
    public void processCompleted(ActivitiEntity activitiEntity) {
        logger.info("流程结束：{}", JSON.toJSONString(activitiEntity));
    }

    @Override
    public String getDsNumberByRoleName(String roleName) {
        return "tfa1236521452192";
    }

    @Override
    public void taskNodeDelete(ActivitiEntity activitiEntity) {
        logger.info("节点删除：{}", JSON.toJSONString(activitiEntity));
    }
}
