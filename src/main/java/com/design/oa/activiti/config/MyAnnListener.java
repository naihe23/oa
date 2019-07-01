package com.design.oa.activiti.config;

import com.design.oa.dao.AnnouncementMapper;
import com.design.oa.model.Announcement;
import com.design.oa.model.LeaveNote;
import com.design.oa.service.AttenService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class MyAnnListener implements ExecutionListener, Serializable {

     @Autowired
    AnnouncementMapper announcementMapper;

    private Expression message;

    public Expression getMessage() {
        return message;
    }

    public void setMessage(Expression message) {
        this.message = message;
    }

    public void notify(DelegateExecution execution) throws Exception {
        String eventName = execution.getEventName();
        if ("end".equals(eventName)) {
            Announcement announcement = (Announcement) execution.getVariable("announcement");
            announcementMapper= (AnnouncementMapper)ApplicationContextUtils.getObject("announcementMapper");
            announcement.setAnnState("1");
            announcementMapper.updateByPrimaryKeySelective(announcement);
        }
    }
}
