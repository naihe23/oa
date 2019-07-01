package com.design.oa.activiti.config;

import com.design.oa.model.LeaveNote;
import com.design.oa.service.AttenService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class MyExecutionListener implements ExecutionListener, Serializable {

    @Autowired
    AttenService attenService;

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
            LeaveNote leaveNote = (LeaveNote) execution.getVariable("leaveNote");
            attenService= (AttenService)ApplicationContextUtils.getObject("attenServiceImp");
            attenService.insertAttenRecord(leaveNote);
        }
    }
}
