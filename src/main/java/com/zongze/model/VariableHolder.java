package com.zongze.model;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2021/2/24 10:04
 * @Created by xiezz
 */
public class VariableHolder {


    public static final String modelName = "model";
    public static final String modelClass = "modelClass";
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public static VariableBuilder newInstance() {
        return new VariableBuilder();
    }

    public void addProperties(String key,String value){
        this.variables.put(key, value);
    }

    static class VariableBuilder {
        private Map<String, Object> variables = new HashMap<>();

        VariableBuilder setModel(Object model) {
            variables.put(modelName, JSON.toJSONString(model));
            variables.put(modelClass, model.getClass().getName());
            return this;
        }

        VariableBuilder setProperties(String key, String value) {
            variables.put(key, value);
            return this;
        }

        VariableHolder build() {
            VariableHolder variableHolder = new VariableHolder();
            variableHolder.setVariables(variables);
            return variableHolder;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread() {
                @SneakyThrows
                @Override
                public void run() {
                    ToosApply toosApply = new ToosApply();
                    toosApply.setId(15);
                    toosApply.setName("张三");
                    toosApply.setCreateTime(new Date());
                    VariableHolder variableHolder = VariableHolder.newInstance().setModel(toosApply).setProperties("remark", "请假单").setProperties("天数", "3").build();
                    Map<String, Object> variables = variableHolder.getVariables();
                    System.out.println("=====================================");
                    variables.entrySet().forEach(entry -> {
                        System.out.println(Thread.currentThread().getName() + "===" + entry.getKey() + "=" + entry.getValue());
                    });
                    System.out.println("=====================================");
                    String model = (String) variables.get("model");
                    Object object = JSONObject.parseObject(model, Class.forName((String) variables.get("modelClass")));
                    System.out.println(object instanceof ToosApply);
                }
            }.start();
        }

        Thread.sleep(1000);

    }


}
