package com.design.oa.dao;

import com.design.oa.model.AnnRead;

public interface AnnReadMapper {
    int deleteByPrimaryKey(Integer annReadId);

    int insert(AnnRead record);

    int insertSelective(AnnRead record);

    AnnRead selectByPrimaryKey(Integer annReadId);

    int updateByPrimaryKeySelective(AnnRead record);

    int updateByPrimaryKey(AnnRead record);

    AnnRead selectByAnnIdAndUserId(AnnRead annRead);
}