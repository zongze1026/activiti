package com.zongze.model;

import lombok.Getter;

/**
 * @Date 2021/2/25 16:04
 * @Created by xiezz
 */
@Getter
public enum ActivitiBusinessType {
    LEAVE("multi01", "processes/multi01.bpmn", "请假审批"),
    LEAVE02("multi02", "processes/multi02.bpmn", "请假审批");


    /**
     * 流程key
     */
    private String processKey;
    /**
     * 审批描述
     */
    private String remark;

    /**
     * 文件名称
     */
    private String fileName;

    ActivitiBusinessType(String processKey, String fileName, String remark) {
        this.processKey = processKey;
        this.fileName = fileName;
        this.remark = remark;
    }



    /**
     * 获取业务流程
     * @param processKey
     * @return com.zongze.model.ActivitiBusinessType
     */
    public static ActivitiBusinessType getBusinessType(String processKey){
        for (ActivitiBusinessType businessType:ActivitiBusinessType.values()){
            if(businessType.getProcessKey().equals(processKey)){
                return businessType;
            }
        }
        return null;
    }



}
