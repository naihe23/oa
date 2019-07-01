package com.design.oa.dao;

import com.design.oa.model.Contact;

import java.util.List;

public interface ContactMapper {
    int deleteByPrimaryKey(Integer contactId);

    int insert(Contact record);

    int insertSelective(Contact record);

    Contact selectByPrimaryKey(Integer contactId);

    int updateByPrimaryKeySelective(Contact record);

    int updateByPrimaryKey(Contact record);

    List<Contact> getAllContact(int userId);
}