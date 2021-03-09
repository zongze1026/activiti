package com.zongze.service.demo;

import com.zongze.mapper.ApplyDetailMapper;
import com.zongze.mapper.MoneyApplyMapper;
import com.zongze.model.ActivitiBusinessType;
import com.zongze.model.ActivitiEntity;
import com.zongze.model.ApplyDetail;
import com.zongze.model.MoneyApply;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Date 2021/3/9 14:18
 * @Created by xiezz
 */
@SuppressWarnings("all")
@Service
public class KQApplyServiceImpl extends AbstractActivitiService {


    @Autowired
    private ApplyDetailMapper applyDetailMapper;

    @Autowired
    private MoneyApplyMapper moneyApplyMapper;

    public KQApplyServiceImpl(RuntimeService runtimeService, RepositoryService repositoryService, TaskService taskService, HistoryService historyService) {
        super(runtimeService, repositoryService, taskService, historyService, ActivitiBusinessType.LEAVE02);
    }


    @Override
    public void taskCreated(ActivitiEntity activitiEntity) {
        MoneyApply moneyApply = (MoneyApply) activitiEntity.getModel();
        ApplyDetail detail = new ApplyDetail();
        detail.setTaskType(activitiEntity.getReviewType().getFlag());
        detail.setEngineeringGroupId(moneyApply.getEngineeringGroupId());
        detail.setEngineeringChildGroup(moneyApply.getEngineeringChildGroup());
        detail.setTaskId(activitiEntity.getTaskId());
        detail.setProcessInstanceId(activitiEntity.getProcessInstanceId());
        detail.setNodeRemark(activitiEntity.getNodeRemark());
        String dsNumber = StringUtils.isEmpty(activitiEntity.getDsNumber()) ? moneyApply.getDsNumber() : activitiEntity.getDsNumber();
        detail.setDsNumber(dsNumber);
        applyDetailMapper.insertSelective(detail);
    }

    @Override
    public void taskCompleted(ActivitiEntity activitiEntity) {
        ApplyDetail find = new ApplyDetail();
        find.setTaskId(activitiEntity.getTaskId());
        find.setProcessInstanceId(activitiEntity.getProcessInstanceId());
        ApplyDetail applyDetail = applyDetailMapper.selectActivitiDetail(find);
        find.setId(applyDetail.getId());
        find.setState(activitiEntity.getReviewFlag().getFlag());
        find.setRemark(activitiEntity.getCommitRemark());
        applyDetailMapper.updateByPrimaryKeySelective(find);
    }

    @Override
    public void processCompleted(ActivitiEntity activitiEntity) {
        MoneyApply moneyApply = (MoneyApply) activitiEntity.getModel();
        MoneyApply update = new MoneyApply();
        update.setId(moneyApply.getId());
        update.setState(activitiEntity.getReviewFlag().getFlag());
        moneyApplyMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public String getDsNumberByRoleName(String roleName) {
        return "tfa2386173198918";
    }



    public void commitTask(MoneyApply moneyApply){
        moneyApplyMapper.insertSelective(moneyApply);
        ActivitiEntity activitiEntity = ActivitiEntity.newBuilder().setModel(moneyApply).build();
        startGeneralTask(activitiEntity);
    }


}
