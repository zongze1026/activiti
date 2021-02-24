package com.zongze.model;

import lombok.Data;
import java.util.Map;

/**
 * @Date 2021/2/23 17:07
 * @Created by xiezz
 */
@Data
public class ProcessModel {

    private ToosApply model;

    private String processInstanceId;

    private String key;

    private Map<String,Object> variables;



}
