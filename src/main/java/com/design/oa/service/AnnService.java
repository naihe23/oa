package com.design.oa.service;

import com.design.oa.model.AnnType;
import com.design.oa.model.Announcement;

import java.util.List;
import java.util.Set;

public interface AnnService {
    List<AnnType> getAnnType();

    int createAnn(Announcement announcement);

    Set<Announcement> getAnnouncement(Integer userId);

    int userRead(int annId);

    List<Announcement> getNotReadAnn();

    List<Announcement> getReadAnn();

    List<Announcement> getOverTimeAnn();

    int deleteAnn(int annId);

    int addType(String typeName);

    int updateType(String typeName,int typeId);

    int deleteType(int typeId);

    int updateAnnouncement(Announcement announcement);
}
