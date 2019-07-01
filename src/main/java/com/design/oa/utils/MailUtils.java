package com.design.oa.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailUtils {

    public MimeMessage packageMail(JavaMailSender javaMailSender, String userEmail, String[] mailReceiver, String[] mailCC,
                               String mailContent, String mailTopic, File att) {
        List<String> mailCCList = new ArrayList<>();
        List<String> mailReceiverList = new ArrayList<>();
        for (String s : mailCC) {

            if (!s.equals("undefined")) {
                mailCCList.add(s);
            }
        }
        for (String s : mailReceiver) {
            if (!s.equals("undefined")) {
                mailReceiverList.add(s);
            }
        }
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(userEmail);
            helper.setTo(mailReceiverList.toArray(new String[mailReceiverList.size()]));
            helper.setCc(mailCCList.toArray(new String[mailCCList.size()]));
            helper.setSubject(mailTopic);
            helper.setText(mailContent);
            helper.setSentDate(new Date());
            if (att != null)
                helper.addAttachment(att.getName(), att);
            message.saveChanges();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Store connectMailBox(String userName, String userPassword){
        try {
            String host = "localhost";
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "imap");
            properties.setProperty("mail.imap.host", host);
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imap");
            store.connect(host, userName, userPassword);
            return store;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
