package com.design.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.design.oa.model.Contact;
import com.design.oa.service.ContactService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class ContactController {

    @Resource
    ContactService contactService;

    @GetMapping("/contacts")
    public String getContacts(int curPage){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(curPage, 4);
        List<Contact> contactList = contactService.getAllContact();
        PageInfo<Contact> pageList = new PageInfo<>(contactList);
        PageHelper.clearPage();
        jsonObject.put("msg", pageList);
        return jsonObject.toString();
    }
    @PostMapping("/contact")
    public String addContact(@RequestBody Contact contact){
        JSONObject jsonObject = new JSONObject();
        int state = contactService.addContact(contact);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @PostMapping("/update/contact")
    public String updateContact(@RequestBody Contact contact){
        JSONObject jsonObject = new JSONObject();
        int state = contactService.updateContact(contact);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }

    @GetMapping("/delete/contact")
    public String deleteContact( int contactId){
        JSONObject jsonObject = new JSONObject();
        int state = contactService.deleteContact(contactId);
        jsonObject.put("msg", state);
        return jsonObject.toString();
    }
}
