package com.design.oa.service.impl;

import com.design.oa.dao.EmailMapper;
import com.design.oa.service.EmailService;
import com.design.oa.utils.MailUtils;
import com.design.oa.utils.ReceiverOneMail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmailServiceImp implements EmailService {

    @Resource
    JavaMailSender javaMailSender;

    @Override
    public int sendMail(String userEmail, String[] mailReceiver, String[] mailCC,
                        String mailContent, String mailTopic, File att) {
        try {
            MailUtils mailUtils = new MailUtils();
            MimeMessage message = mailUtils.packageMail(javaMailSender, userEmail, mailReceiver,
                    mailCC, mailContent, mailTopic, att);
            javaMailSender.send(message);
            return 201;
        } catch (Exception e) {
            e.printStackTrace();
            return 401;
        }
    }

    @Override
    public int saveMail(String userEmail, String[] mailReceiver, String[] mailCC,
                        String mailContent, String mailTopic, File att, String userPassword) {
        String host = "localhost";
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "143");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        try {
            Properties prop = new Properties();
            Session session1 = Session.getDefaultInstance(prop, null);
            Store store = session1.getStore("imap");
            store.connect(host, userEmail, userPassword);
            Folder folder = store.getFolder("INBOX");
            MailUtils mailUtils = new MailUtils();
            MimeMessage message = mailUtils.packageMail(javaMailSender, userEmail, mailReceiver,
                    mailCC, mailContent, mailTopic, att);
            message.setFlag(Flags.Flag.DRAFT, true);
            Message[] messages = {message};
            folder.appendMessages(messages);
            return 201;
        } catch (Exception e) {
            e.printStackTrace();
            return 401;
        }
    }

    @Override
    public List<Map<String, Object>> getDraftBox(String userName, String userPassword) {
        try {
            MailUtils mailUtils = new MailUtils();
            Store store = mailUtils.connectMailBox(userName, userPassword);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            SearchTerm searchTerm = new FlagTerm(new Flags(Flags.Flag.DRAFT), true);
            Message[] messages = folder.search(searchTerm);
            ReceiverOneMail receiverOneMail = new ReceiverOneMail(messages);
            List<Map<String, Object>> list = receiverOneMail.parseMessage();
            folder.close();
            store.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getInbox(String userName, String userPassword) {
        try {
            MailUtils mailUtils = new MailUtils();
            Store store = mailUtils.connectMailBox(userName, userPassword);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            SearchTerm searchTerm = new FlagTerm(new Flags(Flags.Flag.DRAFT), false);
            Message[] messages = folder.search(searchTerm);
            ReceiverOneMail receiverOneMail = new ReceiverOneMail(messages);
            List<Map<String, Object>> list = receiverOneMail.parseMessage();
            folder.close();
            store.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getOutbox(String userName, String userPassword) {
        try {
            Folder folder = null;
            List<Map<String, Object>> list = null;
            MailUtils mailUtils = new MailUtils();
            Store store = mailUtils.connectMailBox(userName, userPassword);
            Folder folders = store.getDefaultFolder();
            Folder[] folders1 = folders.list();
            for (Folder folder1 : folders1) {
                if (folder1.getName().equals("Sent")) {
                    folder = store.getFolder("Sent");
                    folder.open(Folder.READ_ONLY);
                    SearchTerm searchTerm = new FromTerm(new InternetAddress(userName));
                    Message[] messages = folder.search(searchTerm);
                    ReceiverOneMail receiverOneMail = new ReceiverOneMail(messages);
                    list = receiverOneMail.parseMessage();
                    folder.close();
                    store.close();
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> editFlagOfSeen(int mailNumber, String userName, String userPassword) throws Exception {
        MailUtils mailUtils = new MailUtils();
        Store store = mailUtils.connectMailBox(userName, userPassword);
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        Message message = folder.getMessage(mailNumber);
        message.setFlag(Flags.Flag.SEEN, true);
        folder.close(true);
        store.close();
        List<Map<String, Object>> list = getInbox(userName, userPassword);
        return list;
    }

    @Override
    public List<Map<String, Object>> deleteInobxMail(int mailNumber, String userName, String userPassword) {
        try {
            MailUtils mailUtils = new MailUtils();
            Store store = mailUtils.connectMailBox(userName, userPassword);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            Message message = folder.getMessage(mailNumber);
            message.setFlag(Flags.Flag.DELETED, true);
            folder.close(true);
            store.close();
            List<Map<String, Object>> list = getInbox(userName, userPassword);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> deleteOutBoxMail(int mailNumber, String userName, String userPassword) {
        try {
            Folder folder = null;
            List<Map<String, Object>> list = null;
            MailUtils mailUtils = new MailUtils();
            Store store = mailUtils.connectMailBox(userName, userPassword);
            Folder folders = store.getDefaultFolder();
            Folder[] folders1 = folders.list();
            for (Folder folder1 : folders1) {
                if (folder1.getName().equals("Sent")) {
                    folder = store.getFolder("Sent");
                    folder.open(Folder.READ_WRITE);
                    Message message = folder.getMessage(mailNumber);
                    message.setFlag(Flags.Flag.DELETED, true);
                    folder.close(true);
                    store.close();
                    list = getOutbox(userName, userPassword);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> queryMail(String userEmail, String mailBox, String[] mailSender, String[] mailReceiver, String[] mailSendDate, String mailTopic, String userPassword) {
        try {
            List<Map<String, Object>> list = null;
            if (mailBox.equals("收件箱") || mailBox.equals("草稿箱")) {
                MailUtils mailUtils = new MailUtils();
                Store store = mailUtils.connectMailBox(userEmail, userPassword);
                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);
                List<SearchTerm> searchTerms = new ArrayList<>();
                if (!mailSender[0].equals("undefined")) {
                    for (String s : mailSender) {
                        searchTerms.add(new FromStringTerm(s));
                    }
                }
                if (mailBox.equals("收件箱")) {
                    searchTerms.add(new FlagTerm(new Flags(Flags.Flag.DRAFT), false));
                } else
                    searchTerms.add(new FlagTerm(new Flags(Flags.Flag.DRAFT), true));
                if (!mailReceiver[0].equals("undefined")) {
                    for (String s : mailReceiver) {
                        searchTerms.add(new RecipientStringTerm(Message.RecipientType.TO, s));
                        searchTerms.add(new RecipientStringTerm(Message.RecipientType.CC, s));
                    }
                }
                if (!mailSendDate[0].equals("undefined")) {

                    Date date = new Date(mailSendDate[0]);
                    searchTerms.add(new SentDateTerm(ComparisonTerm.GE, date));

                    Date date1 = new Date(mailSendDate[1]);
                    searchTerms.add(new SentDateTerm(ComparisonTerm.LE, date1));
                }
                if (!mailTopic.equals("undefined")) {
                    searchTerms.add(new SubjectTerm(mailTopic));
                }
                SearchTerm searchTerm = new AndTerm(searchTerms.toArray(new SearchTerm[searchTerms.size()]));
                Message[] messages = folder.search(searchTerm);
                ReceiverOneMail receiverOneMail = new ReceiverOneMail(messages);
                list = receiverOneMail.parseMessage();
                folder.close();
                store.close();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
