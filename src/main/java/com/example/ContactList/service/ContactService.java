package com.example.ContactList.service;

import com.example.ContactList.entity.Contact;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {

    ResponseEntity<List<Contact>> getAllContactsByUser(String email);
    ResponseEntity<Contact> saveContact(String email, Contact contact);
    ResponseEntity deleteContact(String email, int contactId);
    ResponseEntity<Contact> updateContact(String email, Contact contact);
    ResponseEntity<Contact> getContact(String email, int contactId);
}
