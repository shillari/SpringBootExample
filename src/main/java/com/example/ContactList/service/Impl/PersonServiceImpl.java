package com.example.ContactList.service.Impl;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import com.example.ContactList.entity.database.User;
import com.example.ContactList.entity.mapper.PersonMapper;
import com.example.ContactList.repository.UserRepository;
import com.example.ContactList.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final UserRepository userRepository;
    private final PersonMapper personMapper;

    @Override
    public ResponseEntity<Person> getPerson(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        // If user not found
        if (user == null || user.get() == null)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(personMapper.mapPerson(user.get()));
    }

    @Override
    public ResponseEntity<Person> saveContact(Person person, Contact contact) {
        return null;
    }

    @Override
    public ResponseEntity<Person> deleteContact(Person person, Contact contact) {
        return null;
    }

    @Override
    public ResponseEntity<Person> updateContact(Person person, Contact contact) {
        return null;
    }
}
