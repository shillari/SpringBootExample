package com.example.ContactList.service;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    ResponseEntity<Person> getPerson(String email);
    ResponseEntity<Person> saveContact(Person person, Contact contact);
    ResponseEntity<Person> deleteContact(Person person, Contact contact);
    ResponseEntity<Person> updateContact(Person person, Contact contact);
}
