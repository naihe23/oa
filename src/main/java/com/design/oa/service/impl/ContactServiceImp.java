package com.design.oa.service.impl;

import com.design.oa.dao.ContactMapper;
import com.design.oa.model.Admin;
import com.design.oa.model.Contact;
import com.design.oa.model.User;
import com.design.oa.service.ContactService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContactServiceImp implements ContactService {

    @Resource
    ContactMapper contactMapper;

    @Override
    public List<Contact> getAllContact() {
        Object object = (Object) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if (object instanceof User) {
            User user = (User) object;
            List<Contact> contactList = contactMapper.getAllContact(user.getUserId());
            return contactList;
        } else if (object instanceof Admin) {
            Admin admin = (Admin) object;
            return null;
        }
        return null;
    }

    @Override
    public int addContact(Contact contact) {
        User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        contact.setUserId(user.getUserId());
        int state = contactMapper.insertSelective(contact);
        if (state > 0) {
            return 201;
        }
        return 401;
    }

    @Override
    public int updateContact(Contact contact) {
        int state = contactMapper.updateByPrimaryKeySelective(contact);
        if(state>0){
            return 201;
        }
        return 401;
    }

    @Override
    public int deleteContact(int contactId) {
        int state = contactMapper.deleteByPrimaryKey(contactId);
        if(state>0){
            return 201;
        }
        return 401;
    }

}
