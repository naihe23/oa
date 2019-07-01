package com.design.oa.dao;

import com.design.oa.model.Text;

import java.util.List;

public interface TextMapper {
    int deleteByPrimaryKey(Integer textId);

    int insert(Text record);

    int insertSelective(Text record);

    Text selectByPrimaryKey(Integer textId);

    int updateByPrimaryKeySelective(Text record);

    int updateByPrimaryKey(Text record);

    List<Text> selectAllTexts();
}