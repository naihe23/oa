package com.design.oa.dao;

import com.design.oa.model.AnnRoleKey;
import org.apache.ibatis.annotations.Param;

public interface AnnRoleMapper {
    int deleteByPrimaryKey(AnnRoleKey key);

    int insert(AnnRoleKey record);

    int insertSelective(AnnRoleKey record);

    int insertWithRole(@Param("annId") Integer annId, @Param("role") int[] role);
}