package com.zongze.mapper;


import com.zongze.model.MoneyApply;

public interface MoneyApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyApply record);

    int insertSelective(MoneyApply record);

    MoneyApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyApply record);

    int updateByPrimaryKey(MoneyApply record);
}