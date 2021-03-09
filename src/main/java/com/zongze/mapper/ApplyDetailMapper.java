package com.zongze.mapper;


import com.zongze.model.ApplyDetail;

public interface ApplyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplyDetail record);

    int insertSelective(ApplyDetail record);

    ApplyDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplyDetail record);

    int updateByPrimaryKey(ApplyDetail record);

    ApplyDetail selectActivitiDetail(ApplyDetail find);
}