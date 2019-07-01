package com.design.oa.dao;

import com.design.oa.model.AnnDeparKey;
import org.apache.ibatis.annotations.Param;

public interface AnnDeparMapper {
    int deleteByPrimaryKey(AnnDeparKey key);

    int insert(AnnDeparKey record);

    int insertSelective(AnnDeparKey record);

    int insertWithDepar(@Param("annId") int annId,@Param("department") int[] department);
}