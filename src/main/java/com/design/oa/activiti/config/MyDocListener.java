package com.design.oa.activiti.config;

import com.design.oa.dao.AnnouncementMapper;
import com.design.oa.dao.OutgoingMessageMapper;
import com.design.oa.dao.TextMapper;
import com.design.oa.model.Announcement;
import com.design.oa.model.OutgoingMessage;
import com.design.oa.model.Text;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class MyDocListener implements ExecutionListener, Serializable {

     @Autowired
     OutgoingMessageMapper outgoingMessageMapper;

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
            int outgoingMessageId = (int)execution.getVariable("outgoingMessageId");
            outgoingMessageMapper= (OutgoingMessageMapper)ApplicationContextUtils.getObject("outgoingMessageMapper");
            OutgoingMessage outgoingMessage = new OutgoingMessage();
            outgoingMessage.setOutgoingMessageId(outgoingMessageId);
            outgoingMessage.setOutgoingMessageState(1);
            outgoingMessageMapper.updateByPrimaryKeySelective(outgoingMessage);
        }
    }
}
