package com.design.oa.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface EmailService {
    int sendMail(String userEmail, String[] mailReceiver, String[] mailCC, String mailContent, String mailTopic, File att) throws Exception;

    List<Map<String, Object>> getInbox(String emailAccount, String mailPassword);

    List<Map<String, Object>> getOutbox(String userName, String userPassword);

    List<Map<String, Object>> editFlagOfSeen(int mailNumber,String userName,String userPassword)throws Exception;

    List<Map<String, Object>> deleteInobxMail(int mailNumber, String userName, String userPassword) ;

    int saveMail(String userEmail, String[] mailReceiver, String[] mailCC,
                        String mailContent, String mailTopic, File att,String userPassword);

    List<Map<String, Object>> getDraftBox(String userName, String userPassword);

    List<Map<String, Object>> deleteOutBoxMail(int mailNumber, String userEmail, String userPassword);

    List<Map<String, Object>> queryMail(String userEmail,String mailBox, String[] mailSender, String[] mailReceiver, String[] mailSendDate, String mailTopic, String userPassword);
}
