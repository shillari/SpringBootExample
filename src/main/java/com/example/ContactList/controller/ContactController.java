package com.example.ContactList.controller;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import com.example.ContactList.service.ContactService;
import com.example.ContactList.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    @Autowired
    private PersonService personService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/user")
    public ResponseEntity<Person> getPerson(@RequestParam String email) {
        return personService.getPerson(email);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContactsByUser(@RequestParam String email) {
        return contactService.getAllContactsByUser(email);
    }

    @PostMapping("/contact")
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact,
                                               @RequestParam String email) {
        return contactService.saveContact(email, contact);
    }

    @DeleteMapping("/contact")
    public ResponseEntity deleteContact(@RequestParam String email,
                                        @RequestParam int contactId) {
        return contactService.deleteContact(email, contactId);
    }

    @GetMapping("/contact")
    public ResponseEntity<Contact> getContact(@RequestParam String email,
                                              @RequestParam int contactId) {
        return contactService.getContact(email, contactId);
    }

    @PutMapping("/contact")
    public ResponseEntity<Contact> updateContact(@RequestParam String email,
                                                 @RequestBody Contact contact) {
        return contactService.updateContact(email, contact);
    }

}
