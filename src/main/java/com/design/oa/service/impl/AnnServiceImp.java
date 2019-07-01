package com.design.oa.service.impl;

import com.design.oa.dao.*;
import com.design.oa.model.*;
import com.design.oa.service.AnnService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(value = "test1TransactionManager")
public class AnnServiceImp implements AnnService {

    @Resource
    AnnTypeMapper annTypeMapper;
    @Resource
    AnnouncementMapper announcementMapper;
    @Resource
    AnnDeparMapper annDeparMapper;
    @Resource
    AnnUserMapper annUserMapper;
    @Resource
    AnnRoleMapper annRoleMapper;
    @Resource
    AnnReadMapper annReadMapper;

    @Override
    public List<AnnType> getAnnType() {
        List<AnnType> annTypeList = annTypeMapper.selectAllType();
        return annTypeList;
    }

    @Override
    public int createAnn(Announcement announcement) {
        User user = new User();
        int isInsert = 0;
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        announcement.setUserId(user.getUserId());
        if (announcement.getAnnRange() != null && announcement.getAnnRange().equals("true")) {
            announcement.setAnnRange("所有人员");
            int state = announcementMapper.insertSelective(announcement);
            if (state > 0)
                return 201;
            else
                return 401;
        } else {
            announcement.setAnnRange("部分人员");
            int state = announcementMapper.insertSelective(announcement);
            if (announcement.getDepartment() != null) {
                isInsert = annDeparMapper.insertWithDepar(announcement.getAnnId(), announcement.getDepartment());

            }
            if (announcement.getUser() != null) {
                isInsert = annUserMapper.insertWithUser(announcement.getAnnId(), announcement.getUser());
            }
            if (announcement.getRole() != null) {
                isInsert = annRoleMapper.insertWithRole(announcement.getAnnId(), announcement.getRole());
            }
            if (isInsert > 0)
                return 201;
            else
                return 401;
        }
    }

    @Override
    public Set<Announcement> getAnnouncement(Integer userId) {
        List<Announcement> announcements = announcementMapper.selectByAllUser();
        List<Announcement> announcementsUser = announcementMapper.selectByUserId(userId);
        List<Announcement> announcementsDepar = announcementMapper.selectByDepar(userId);
        for (Announcement announcement : announcementsDepar) {
            Announcement announcement1 = announcementMapper.selectUserAndDepar(announcement.getAnnId());
            announcement.setAnnCreater(announcement1.getAnnCreater());
            announcement.setUserDepartment(announcement1.getUserDepartment());
        }
        List<Announcement> announcementsRole = announcementMapper.selectByRole(userId);
        List<Announcement> announcementList = new ArrayList<>();
        announcementList.addAll(announcements);
        announcementList.addAll(announcementsUser);
        announcementList.addAll(announcementsDepar);
        announcementList.addAll(announcementsRole);
        Set<Announcement> annList = new HashSet<>(announcementList);
        return annList;
    }

    @Override
    public int userRead(int annId) {
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        AnnRead annRead = new AnnRead();
        annRead.setAnnId(annId);
        annRead.setUserId(user.getUserId());
        AnnRead annRead1 = annReadMapper.selectByAnnIdAndUserId(annRead);
        if (annRead1 == null) {
            int isRead = annReadMapper.insert(annRead);
            if (isRead > 0) {
                return 201;
            } else
                return 401;
        } else
            return 402;
    }

    @Override
    public List<Announcement> getNotReadAnn() {
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<Announcement> announcements = announcementMapper.getNotReadAnn(user.getUserId());
        Iterator<Announcement> iterator = announcements.iterator();
        Set<Announcement> announcements1 = getAnnouncement(user.getUserId());
        Iterator<Announcement> iterator1 = null;
        while (iterator.hasNext()) {
            Announcement announcement = iterator.next();
            iterator1 = announcements1.iterator();
            while (iterator1.hasNext()) {
                Announcement announcement1 = iterator1.next();
                if (announcement1.getAnnId() == announcement.getAnnId()) {
                    iterator1.remove();
                }
            }
        }
        return new ArrayList<>(announcements1);
    }

    @Override
    public List<Announcement> getReadAnn() {
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        List<Announcement> announcements = announcementMapper.getReadAnn(user.getUserId());
        return announcements;
    }

    @Override
    public List<Announcement> getOverTimeAnn() {
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        Set<Announcement> announcements = getAnnouncement(user.getUserId());
        Iterator<Announcement> iterator = announcements.iterator();
        List<Announcement> announcements1 = new ArrayList<>();
        while (iterator.hasNext()) {
            Announcement announcement = iterator.next();
            Date date = new Date();
            if (announcement.getAnnEndTime().before(date)) {
                announcements1.add(announcement);
            }
        }
        return announcements1;
    }

    @Override
    public int deleteAnn(int annId) {
        User user = new User();
        Object o = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (o instanceof User) {
            user = (User) o;
        }
        Announcement announcement = announcementMapper.selectByPrimaryKey(annId);
        if (announcement != null && announcement.getUserId() == user.getUserId()) {
            announcementMapper.deleteByPrimaryKey(annId);
            return 201;
        } else if (announcement != null && announcement.getUserId() != user.getUserId()) {
            return 401;
        } else
            return 402;
    }

    @Override
    public int addType(String typeName) {
        AnnType annType = new AnnType();
        annType.setTypeName(typeName);
        int state = annTypeMapper.insertSelective(annType);
        if (state > 0)
            return 201;
        else
            return 401;
    }

    @Override
    public int updateType(String typeName, int typeId) {
        AnnType annType = new AnnType();
        annType.setTypeName(typeName);
        annType.setTypeId(typeId);
        int state = annTypeMapper.updateByPrimaryKeySelective(annType);
        if (state > 0)
            return 201;
        else
            return 401;
    }

    @Override
    public int deleteType(int typeId) {
        int state = annTypeMapper.deleteByPrimaryKey(typeId);
        if (state > 0)
            return 201;
        else
            return 401;
    }

    @Override
    public int updateAnnouncement(Announcement announcement) {
        int state = announcementMapper.updateByPrimaryKeySelective(announcement);
        if (state > 0)
            return 201;
        else
            return 401;
    }
}
