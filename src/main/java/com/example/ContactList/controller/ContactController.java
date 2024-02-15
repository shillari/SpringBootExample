package com.example.ContactList.controller;

import com.example.ContactList.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Validated
@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    @GetMapping
    public ResponseEntity<Person> getPerson(@RequestParam String name) {
        return ResponseEntity.ok(new Person(1, name, "bobo", "email@email.com", new ArrayList<>()));
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person,
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
