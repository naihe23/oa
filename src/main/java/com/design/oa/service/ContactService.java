package com.design.oa.service;

import com.design.oa.model.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContact();

    int addContact(Contact contact);

    int updateContact(Contact contact);

    int deleteContact(int contactId);
}
