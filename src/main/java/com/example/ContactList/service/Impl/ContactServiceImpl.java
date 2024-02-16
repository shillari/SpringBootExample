package com.example.ContactList.service.Impl;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import com.example.ContactList.entity.mapper.ContactListMapper;
import com.example.ContactList.entity.mapper.PersonMapper;
import com.example.ContactList.repository.ContactListRepository;
import com.example.ContactList.repository.UserRepository;
import com.example.ContactList.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final UserRepository userRepository;
    private final ContactListRepository contactListRepository;

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private ContactListMapper contactListMapper;

    @Override
    public ResponseEntity<Set<Contact>> getAllContactsByUser(String email) {
        Set<ContactList> scl = userRepository.findAllContactLists(email);
        // if there is no contact list for this user
        if (scl == null)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(contactListMapper.mapAllContacts(scl));
    }

    @Override
    public ResponseEntity<Contact> saveContact(String email, Contact contact) {
        Optional<User> user = userRepository.findByEmail(email);
        // If user not found
        if (user == null || !user.isPresent())
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT).build();

        // Map entity to DTO
        ContactList cl = contactListMapper.mapContact(contact, user.get());

        // Check if already exists this contact to this user
        if (contactListRepository.findContactList(user.get(), cl.getContact()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        contactListRepository.save(cl);

        return ResponseEntity.ok(contact);
    }

    @Override
    public ResponseEntity<Contact> deleteContact(Person person, Contact contact) {
        return null;
    }

    @Override
    public ResponseEntity<Contact> updateContact(Person person, Contact contact) {
        return null;
    }
}
