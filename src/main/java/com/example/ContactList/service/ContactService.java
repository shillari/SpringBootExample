package com.example.ContactList.service;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ContactService {

    ResponseEntity<Set<Contact>> getAllContactsByUser(String email);
    ResponseEntity<Contact> saveContact(String email, Contact contact);
    ResponseEntity deleteContact(String email, String contact);
    ResponseEntity<Contact> updateContact(Person person, Contact contact);
}
