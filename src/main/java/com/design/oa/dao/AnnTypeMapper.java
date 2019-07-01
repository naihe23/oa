package com.design.oa.dao;

import com.design.oa.model.AnnType;

import java.util.List;

public interface AnnTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(AnnType record);

    int insertSelective(AnnType record);

    AnnType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(AnnType record);

    int updateByPrimaryKey(AnnType record);

    List<AnnType> selectAllType();
}