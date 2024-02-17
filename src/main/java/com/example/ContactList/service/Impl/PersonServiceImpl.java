package com.example.ContactList.service.Impl;

import com.example.ContactList.entity.Person;
import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import com.example.ContactList.entity.mapper.ContactListMapper;
import com.example.ContactList.entity.mapper.PersonMapper;
import com.example.ContactList.repository.UserRepository;
import com.example.ContactList.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private ContactListMapper contactListMapper;

    @Override
    public ResponseEntity<Person> getPerson(String email) {
        Optional<User> user = userRepository.findByEmailWithContactLists(email);
        // If user not found
        if (user == null || !user.isPresent())
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(personMapper.mapPerson(user.get()));
    }

}
