package com.design.oa.dao;

import com.design.oa.model.Announcement;

import java.util.HashMap;
import java.util.List;

public interface AnnouncementMapper {
    int deleteByPrimaryKey(Integer annId);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    Announcement selectByPrimaryKey(Integer annId);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKeyWithBLOBs(Announcement record);

    int updateByPrimaryKey(Announcement record);

    List<Announcement> selectByAllUser();

    List<Announcement> selectByUserId(Integer userId);

    List<Announcement> selectByDepar(Integer userId);

    List<Announcement> selectByRole(Integer userId);

    List<Announcement> getNotReadAnn(int userId);

    List<Announcement> getReadAnn(Integer userId);

    Announcement selectUserAndDepar(Integer annId);
}