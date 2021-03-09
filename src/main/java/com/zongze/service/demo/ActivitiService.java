package com.zongze.service.demo;

import com.zongze.model.ActivitiEntity;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import java.util.List;

/**
 * @Date 2021/2/24 10:47
 * @Created by xiezz
 */
public interface ActivitiService {


    /**
     * 部署流程
     */
    Deployment deploy();


    /**
     * 部署流程
     *
     * @param processKey
     * @return void
     */
    Deployment deploy(String processKey);


    /**
     * 提交任务
     *
     * @param taskId
     */
    void commitTask(String taskId, ActivitiEntity.ReviewFlag reviewFlag, String remark);


    /**
     * 提交任务
     *
     * @param task
     * @param reviewFlag
     * @param remark
     * @return void
     */
    void commitTask(Task task, ActivitiEntity.ReviewFlag reviewFlag, String remark);


    /**
     * 开启普通流程
     *
     * @param activitiEntity
     * @return java.lang.String 返回流程id
     */
    String startGeneralTask(ActivitiEntity activitiEntity);


    /**
     * 开启多任务会签流程
     *
     * @param activitiEntity
     * @param dsNumbers      参与会签的得胜号
     * @param factor         条件参数
     * @param contains       是否包含临界值
     * @return java.lang.String
     */
    String startMultiTask(ActivitiEntity activitiEntity, List<String> dsNumbers, Double factor, boolean contains);


    /**
     * 获取任务列表
     *
     * @param processInstanceId
     * @return java.util.List<org.activiti.engine.task.Task>
     */
    List<Task> getTasks(String processInstanceId);


    /**
     * 业务分发
     *
     * @param activitiEntity
     * @param activitiEvent
     */
    void dispatch(ActivitiEntity activitiEntity, ActivitiEvent activitiEvent);


    /**
     * 任务创建阶段记录处理流程，对应activiti中每个任务
     *
     * @param activitiEntity
     */
    void taskCreated(ActivitiEntity activitiEntity);


    /**
     * 任务完成阶段，修改任务审核状态
     *
     * @param activitiEntity
     */
    void taskCompleted(ActivitiEntity activitiEntity);


    /**
     * 整体任务已经完成，记录审批结果
     *
     * @param activitiEntity
     */
    void processCompleted(ActivitiEntity activitiEntity);


    /**
     * 通过角色名称获取得盛号
     *
     * @return java.lang.String
     */
    String getDsNumberByRoleName(String roleName);



    /**
     * 任务节点删除
     * @param activitiEntity
     * @return void
     */
    void taskNodeDelete(ActivitiEntity activitiEntity);


}
