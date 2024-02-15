package com.example.ContactList.controller;

import com.example.ContactList.entity.Person;
import com.example.ContactList.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

//@Validated
@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<Person> getPerson(@RequestParam String email) {
        return personService.getPerson(email);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(//@Valid
                                               @RequestBody Person person,
                                               BindingResult result) {
        if(result.hasErrors()) {
            // Log validation errors
            for (FieldError error : result.getFieldErrors()) {
                System.out.println("Validation error: " + error.getDefaultMessage());
            }
            return ResponseEntity.ok().build();
        }
        // TODO: Add person
        return ResponseEntity.ok().body(person);
    }

}
