package com.design.oa.dao;

import com.design.oa.model.AnnUserKey;
import org.apache.ibatis.annotations.Param;

public interface AnnUserMapper {
    int deleteByPrimaryKey(AnnUserKey key);

    int insert(AnnUserKey record);

    int insertSelective(AnnUserKey record);

    int insertWithUser(@Param("annId")Integer annId,@Param("user") int[] user);
}