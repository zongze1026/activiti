package com.zongze.model;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2021/2/24 10:04
 * @Created by xiezz
 */
public class ActivitiEntity {
    private static final String modelKey = "model";
    private static final String modelClass = "modelClass";
    private static final String processClass = "processClass";
    private static final String reviewFlagKey = "flag";
    private static final String nodeFlagKey = "nodeFlag";
    private static final String reviewTypeKey = "reviewType";
    private static final String remarkKey = "remark";
    private static final String userList = "userList";
    private static final String passFactor = "passFactor";
    private static final String factorContainsFlag = "factorContainsFlag";
    private static final String passCount = "passCount";
    private static final String rejectCount = "rejectCount";
    private static final String totalCount = "totalCount";
    private String taskId;
    private String processInstanceId;
    private String nodeRemark;
    private String commitRemark;
    private String roleName;
    private String dsNumber;
    private ReviewFlag reviewFlag;
    private ReviewType reviewType;
    private Map<String, Object> variables;


    /**
     * 通过业务中的key获取值
     *
     * @param key
     * @return java.lang.Object
     */
    public Object getProperties(String key) {
        return variables.get(key);
    }


    /**
     * 获取业务对象
     *
     * @return java.lang.Object
     */
    public Object getModel() {
        String json = (String) variables.get(ActivitiEntity.modelKey);
        String classType = (String) variables.get(ActivitiEntity.modelClass);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(json, clazz);
    }


    /**
     * 获取审批结果
     * @return com.zongze.model.ActivitiEntity.ReviewFlag
     */
    public ReviewFlag getReviewFlag() {
      return reviewFlag;
    }


    /**
     * 获取任务提交类型
     * @return com.zongze.model.ActivitiEntity.ReviewType
     */
    public ReviewType getReviewType() {
       return reviewType;
    }


    public static ActivitiEntityBuilder newBuilder() {
        return new ActivitiEntityBuilder();
    }


    public static String getModelKey() {
        return modelKey;
    }

    public static String getFactorContainsFlag() {
        return factorContainsFlag;
    }

    public static String getModelClass() {
        return modelClass;
    }

    public static String getProcessClass() {
        return processClass;
    }

    public static String getReviewFlagKey() {
        return reviewFlagKey;
    }

    public static String getNodeFlagKey() {
        return nodeFlagKey;
    }

    public static String getReviewTypeKey() {
        return reviewTypeKey;
    }

    public static String getRemarkKey() {
        return remarkKey;
    }

    public static String getUserList() {
        return userList;
    }

    public static String getPassFactor() {
        return passFactor;
    }

    public static String getPassCount() {
        return passCount;
    }

    public static String getRejectCount() {
        return rejectCount;
    }

    public static String getTotalCount() {
        return totalCount;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getNodeRemark() {
        return nodeRemark;
    }

    public void setNodeRemark(String nodeRemark) {
        this.nodeRemark = nodeRemark;
    }

    public String getCommitRemark() {
        return commitRemark;
    }

    public void setCommitRemark(String commitRemark) {
        this.commitRemark = commitRemark;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDsNumber() {
        return dsNumber;
    }

    public void setDsNumber(String dsNumber) {
        this.dsNumber = dsNumber;
    }

    public void setReviewFlag(ReviewFlag reviewFlag) {
        this.reviewFlag = reviewFlag;
    }

    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public static class ActivitiEntityBuilder {
        private Map<String, Object> variables = new HashMap<>();

        public ActivitiEntityBuilder() {
            setReviewFlag(ReviewFlag.AGREE);
            setReviewType(ReviewType.APPLY);
        }

        public ActivitiEntityBuilder setReviewFlag(ReviewFlag reviewFlag) {
            variables.put(reviewFlagKey, reviewFlag.getFlag());
            return this;
        }

        public ActivitiEntityBuilder setReviewType(ReviewType reviewType) {
            variables.put(reviewTypeKey, reviewType.getFlag());
            return this;
        }

        public ActivitiEntityBuilder setModel(Object model) {
            variables.put(modelKey, JSON.toJSONString(model));
            variables.put(modelClass, model.getClass().getName());
            return this;
        }

        public ActivitiEntityBuilder setProperties(String key, String value) {
            variables.put(key, value);
            return this;
        }

        public ActivitiEntityBuilder setProperties(String key, Integer value) {
            variables.put(key, value);
            return this;
        }

        public ActivitiEntityBuilder setProperties(String key, Double value) {
            variables.put(key, value);
            return this;
        }

        public ActivitiEntityBuilder setProperties(String key, Boolean value) {
            variables.put(key, value);
            return this;
        }

        public ActivitiEntity build() {
            ActivitiEntity activitiEntity = new ActivitiEntity();
            activitiEntity.setVariables(variables);
            return activitiEntity;
        }

    }


    public static enum ReviewFlag {
        AGREE(1, "同意"),
        REJECT(2, "拒绝");

        private Integer flag;
        private String remark;

        ReviewFlag(Integer flag, String remark) {
            this.flag = flag;
            this.remark = remark;
        }

        public static ReviewFlag getReviewFlagInfo(Integer flag) {
            for (ReviewFlag reviewFlag : ReviewFlag.values()) {
                if (reviewFlag.getFlag().equals(flag)) {
                    return reviewFlag;
                }
            }
            return null;
        }

        public Integer getFlag() {
            return flag;
        }

        public String getRemark() {
            return remark;
        }
    }


    public static enum ReviewType {
        APPLY(1, "申请"),
        REVIEW(2, "审核");

        private Integer flag;
        private String remark;

        ReviewType(Integer flag, String remark) {
            this.flag = flag;
            this.remark = remark;
        }

        public static ReviewType getReviewTypeInfo(Integer flag) {
            for (ReviewType reviewType : ReviewType.values()) {
                if (reviewType.getFlag().equals(flag)) {
                    return reviewType;
                }
            }
            return null;
        }

        public Integer getFlag() {
            return flag;
        }

        public String getRemark() {
            return remark;
        }
    }


}
