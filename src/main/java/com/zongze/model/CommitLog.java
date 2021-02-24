package com.zongze.model;

import lombok.Data;

import java.util.Date;

/**
 * @Date 2021/2/23 17:04
 * @Created by xiezz
 */
@Data
public class CommitLog {

    private Integer applyId;

    private String taskId;

    private String name;

    private Date applyTime;

    private Date createTime;

    @Override
    public String toString() {
        return "CommitLog{" +
                "applyId=" + applyId +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", applyTime=" + applyTime +
                ", createTime=" + createTime +
                '}';
    }
}
