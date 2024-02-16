package com.example.ContactList.service;

import com.example.ContactList.entity.Person;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    ResponseEntity<Person> getPerson(String email);
}
