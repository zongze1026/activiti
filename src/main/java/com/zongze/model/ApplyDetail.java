package com.zongze.model;

import java.util.Date;

public class ApplyDetail {
    private Integer id;

    private String taskId;

    private String remark;
    private String nodeRemark;

    private String processInstanceId;

    private Integer engineeringGroupId;

    private Integer engineeringChildGroup;

    private Integer taskType;

    private Integer state;

    private String dsNumber;

    private Date createTime;

    private Date updateTime;


    public String getRemark() {
        return remark;
    }

    public String getNodeRemark() {
        return nodeRemark;
    }

    public void setNodeRemark(String nodeRemark) {
        this.nodeRemark = nodeRemark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId == null ? null : processInstanceId.trim();
    }

    public Integer getEngineeringGroupId() {
        return engineeringGroupId;
    }

    public void setEngineeringGroupId(Integer engineeringGroupId) {
        this.engineeringGroupId = engineeringGroupId;
    }

    public Integer getEngineeringChildGroup() {
        return engineeringChildGroup;
    }

    public void setEngineeringChildGroup(Integer engineeringChildGroup) {
        this.engineeringChildGroup = engineeringChildGroup;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDsNumber() {
        return dsNumber;
    }

    public void setDsNumber(String dsNumber) {
        this.dsNumber = dsNumber == null ? null : dsNumber.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}