package com.design.oa.dao;

import com.design.oa.model.Workflow;

public interface WorkflowMapper {
    int deleteByPrimaryKey(Integer workflowId);

    int insert(Workflow record);

    int insertSelective(Workflow record);

    Workflow selectByPrimaryKey(Integer workflowId);

    int updateByPrimaryKeySelective(Workflow record);

    int updateByPrimaryKey(Workflow record);
}