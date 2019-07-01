package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.model.User;
import com.design.oa.model.UserAuthority;
import com.design.oa.service.EmailService;
import com.design.oa.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    UserService userService;
    @Resource
    EmailService emailService;

    @GetMapping(value = "/users")
    public String getMailUsers() {
        JSONObject jsonObject = new JSONObject();
        List<UserAuthority> list = userService.getAllUser(null);
        if (list.isEmpty()) {
            jsonObject.put("msg", "无用户");
        } else {
            jsonObject.put("msg", list);
        }
        return jsonObject.toString();
    }

    @PostMapping("/send")
    public String sendMail(String[] mailCC, String mailContent, String[] mailReceiver,
                           String mailTopic,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        String filePath = "D://upload";
        //如果目录不存在，自动创建文件夹
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (file == null) {
            int isSend = emailService.sendMail(user.getUserEmail(), mailReceiver, mailCC, mailContent, mailTopic, null);
            jsonObject.put("msg", isSend);
        } else {
            String fileName = file.getOriginalFilename();
            File att = new File(filePath, fileName);
            file.transferTo(att);
            int isSend = emailService.sendMail(user.getUserEmail(), mailReceiver, mailCC, mailContent, mailTopic, att);
            jsonObject.put("msg", isSend);
        }
        return jsonObject.toString();
    }

    @GetMapping("/inbox")
    public HashMap<String, PageInfo> getInbox(String userPassword) throws Exception {
        HashMap<String, PageInfo> map = new HashMap<>();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        PageHelper.startPage(1, 4);
        List<Map<String, Object>> list = emailService.getInbox(user.getUserEmail(), userPassword);
        PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
        PageHelper.clearPage();
        map.put("msg", pageList);
        return map;
    }

    @GetMapping("/outbox")
    public HashMap<String, PageInfo> getOutbox(String userPassword) throws Exception {
        HashMap<String, PageInfo> map = new HashMap<>();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        PageHelper.startPage(1, 4);
        List<Map<String, Object>> list = emailService.getOutbox(user.getUserEmail(), userPassword);
        PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
        PageHelper.clearPage();
        map.put("msg", pageList);
        return map;
    }

    @GetMapping("/draftBox")
    public HashMap<String, PageInfo> getDraftbox(String userPassword) throws Exception {
        HashMap<String, PageInfo> map = new HashMap<>();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        PageHelper.startPage(1, 4);
        List<Map<String, Object>> list = emailService.getDraftBox(user.getUserEmail(), userPassword);
        PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
        PageHelper.clearPage();
        map.put("msg", pageList);
        return map;
    }

    @PostMapping("/fileDowload")
    public String fileDowload(String fileName, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        if (fileName != null) {
            String filePath = "D://upload";
            File file = new File(filePath, fileName);
            if (file.exists()) {
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setContentType("application/octet-stream");
                FileInputStream fileInputStream = null;
                BufferedInputStream bis = null;
                try {
                    byte[] buffer = new byte[1024];
                    fileInputStream = new FileInputStream(file);
                    bis = new BufferedInputStream(fileInputStream);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    jsonObject.put("msg", 201);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        bis.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                }
            }
        }
        return jsonObject.toString();
    }

    @PostMapping("/seen")
    public String seen(int mailNumber, String userPassword) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        try {
            PageHelper.startPage(1, 4);
            List<Map<String, Object>> list = emailService.editFlagOfSeen(mailNumber, user.getUserEmail(), userPassword);
            PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
            PageHelper.clearPage();
            jsonObject.put("msg", 201);
            jsonObject.put("list", pageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @PostMapping("/delete/mail")
    public String deleteMail(int mailNumber, String userPassword) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        try {
            PageHelper.startPage(1, 4);
            List<Map<String, Object>> list = emailService.deleteInobxMail(mailNumber, user.getUserEmail(), userPassword);
            PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
            PageHelper.clearPage();
            jsonObject.put("msg", 201);
            jsonObject.put("list", pageList);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/delete/outbox")
    public String deleteOutBoxMail(int mailNumber, String userPassword) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        try {
            PageHelper.startPage(1, 4);
            List<Map<String, Object>> list = emailService.deleteOutBoxMail(mailNumber, user.getUserEmail(), userPassword);
            PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
            PageHelper.clearPage();
            jsonObject.put("msg", 201);
            jsonObject.put("list", pageList);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/save")
    public String saveMail(String[] mailCC, String mailContent, String[] mailReceiver,
                           String mailTopic, String userPassword,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        String filePath = "D://upload";
        //如果目录不存在，自动创建文件夹
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (file == null) {
            int isSend = emailService.saveMail(user.getUserEmail(), mailReceiver, mailCC, mailContent, mailTopic, null, userPassword);
            jsonObject.put("msg", isSend);
        } else {
            String fileName = file.getOriginalFilename();
            File att = new File(filePath, fileName);
            file.transferTo(att);
            int isSend = emailService.saveMail(user.getUserEmail(), mailReceiver, mailCC, mailContent, mailTopic, att, userPassword);
            jsonObject.put("msg", isSend);
        }
        return jsonObject.toString();
    }

    @PostMapping("/query/mail")
    public String queryMail(String mailBox, String[] mailSender,
                            String[] mailReceiver, @RequestParam(value = "mailSendDate",
            required = false) String[] mailSendDate,
                            String mailTopic, String userPassword) {
        JSONObject jsonObject = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        PageHelper.startPage(1, 4);
        List<Map<String, Object>> list = emailService.queryMail(user.getUserEmail(),mailBox, mailSender, mailReceiver, mailSendDate, mailTopic, userPassword);
        PageInfo<Map<String, Object>> pageList = new PageInfo<>(list);
        PageHelper.clearPage();
        jsonObject.put("msg",201);
        jsonObject.put("list",pageList);
        return jsonObject.toString();
    }
}
