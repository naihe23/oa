package com.design.oa.activiti.config;

import com.design.oa.dao.IncomingMapper;
import com.design.oa.dao.OutgoingMessageMapper;
import com.design.oa.model.Incoming;
import com.design.oa.model.OutgoingMessage;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class MyEndIncListener implements ExecutionListener, Serializable {

     @Autowired
     IncomingMapper incomingMapper;

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
            int incomingId = (int)execution.getVariable("incomingId");
            incomingMapper= (IncomingMapper)ApplicationContextUtils.getObject("incomingMapper");
            Incoming incoming = new Incoming();
            incoming.setIncomingId(incomingId);
            incoming.setIncomingState(1);
            incomingMapper.updateStateById(incoming);
        }
    }
}
