package com.design.oa.service;

import com.design.oa.model.Incoming;
import com.design.oa.model.OutgoingMessage;
import com.design.oa.model.Text;

import java.util.HashMap;
import java.util.List;

public interface DocumentService {
    int saveText(Text text);

    List<Text> getTexts();

    int saveOutgoing(OutgoingMessage outgoingMessage);

    int saveIncoming(Incoming incoming);

    List<HashMap<String,Object>> getOutMessageByUserId(String deploymentId,int sign);

    List<HashMap<String, Object>> getMyOutMessage(String deploymentId);

    List<HashMap<String, Object>> searchDocument(String title, String deploymentId, int outgoingMessageUrgent, int userId);

    List<HashMap<String, Object>> getMyIncMessage(String deploymentId);

    List<HashMap<String, Object>> searchIncoming(String title, String deploymentId, int incomingoutgoingMessageUrgent, int userId);

    List<HashMap<String,Object>> historicTask(int id,String userName);
}
