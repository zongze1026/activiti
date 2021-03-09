package com.zongze.service.demo;

import com.zongze.model.ActivitiBusinessType;
import com.zongze.model.ActivitiEntity;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 * 多人会签任务处理
 *
 * @Date 2021/3/2 10:36
 * @Created by xiezz
 */
public abstract class AbstractMultiProcessActivitiService extends AbstractActivitiService {

    public AbstractMultiProcessActivitiService(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService, HistoryService historyService, ActivitiBusinessType activitiBusinessType) {
        super(runtimeService, repositoryService, taskService, historyService, activitiBusinessType);
    }

    /**
     * 提交任务
     *
     * @param taskId
     * @param reviewFlag
     * @param remark
     * @return void
     */
    @Override
    public void commitTask(String taskId, ActivitiEntity.ReviewFlag reviewFlag, String remark) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!ObjectUtils.isEmpty(task)) {
            boolean match = determineDsNumber(task.getAssignee());
            if (match) {
                lock.lock();
                try {
                    BigDecimal totalBigDecimal = new BigDecimal(getIntProperties(task, ActivitiEntity.getTotalCount()));
                    Double factor = getDoubleProperties(task, ActivitiEntity.getPassFactor());
                    Assert.notNull(factor, "node factors not null");
                    if (ActivitiEntity.ReviewFlag.AGREE.equals(reviewFlag)) {
                        Integer passCount = getIntProperties(task, ActivitiEntity.getPassCount());
                        setProperties(task, ActivitiEntity.getPassCount(), ++passCount);
                        BigDecimal passBigDecimal = new BigDecimal(passCount);
                        boolean contains = getBooleanProperties(task, ActivitiEntity.getFactorContainsFlag());
                        boolean passFlag = contains ? passBigDecimal.divide(totalBigDecimal, 2, BigDecimal.ROUND_DOWN).doubleValue() >= factor
                                : passBigDecimal.divide(totalBigDecimal, 2, BigDecimal.ROUND_DOWN).doubleValue() > factor;
                        if (passFlag) {
                            setProperties(task, ActivitiEntity.getNodeFlagKey(), Boolean.TRUE);
                            super.commitTask(task, ActivitiEntity.ReviewFlag.AGREE, remark);
                        } else {
                            super.commitTask(task, reviewFlag, remark);
                        }
                    } else {
                        Integer rejectCount = getIntProperties(task, ActivitiEntity.getRejectCount());
                        setProperties(task, ActivitiEntity.getRejectCount(), ++rejectCount);
                        BigDecimal rejectBigDecimal = new BigDecimal(rejectCount);
                        if (rejectBigDecimal.divide(totalBigDecimal, 2, BigDecimal.ROUND_DOWN).doubleValue() > (1 - factor)) {
                            setProperties(task, ActivitiEntity.getNodeFlagKey(), Boolean.TRUE);
                            super.commitTask(task, ActivitiEntity.ReviewFlag.REJECT, remark);
                        } else {
                            super.commitTask(task, reviewFlag, remark);
                        }
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                super.commitTask(task, reviewFlag, remark);
            }
        }
    }


    /**
     * 开启一个多人会签任务
     * @param activitiEntity
     * @param dsNumbers
     * @param factor
     * @param contains
     * @return java.lang.String
     */
    public String startMultiTask(ActivitiEntity activitiEntity, List<String> dsNumbers, Double factor, boolean contains) {
        Assert.notEmpty(dsNumbers, "The signer cannot be empty");
        Assert.notNull(factor, "The condition cannot be null");
        activitiEntity.getVariables().put(ActivitiEntity.getPassCount(), 0);
        activitiEntity.getVariables().put(ActivitiEntity.getNodeFlagKey(), false);
        activitiEntity.getVariables().put(ActivitiEntity.getRejectCount(), 0);
        activitiEntity.getVariables().put(ActivitiEntity.getTotalCount(), dsNumbers.size());
        activitiEntity.getVariables().put(ActivitiEntity.getUserList(), dsNumbers);
        activitiEntity.getVariables().put(ActivitiEntity.getPassFactor(), factor);
        activitiEntity.getVariables().put(ActivitiEntity.getFactorContainsFlag(), contains);
        return super.startGeneralTask(activitiEntity);
    }



    /**
     * 任务节点删除
     * @param activitiEntity
     * @return void
     */
    public abstract void  taskNodeDelete(ActivitiEntity activitiEntity);


}
