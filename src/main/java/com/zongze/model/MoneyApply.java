package com.zongze.model;

import java.util.Date;

public class MoneyApply {
    private Integer id;

    private Integer engineeringGroupId;

    private Integer engineeringChildGroup;

    private String name;

    private String amount;

    private Integer state;

    private String dsNumber;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
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