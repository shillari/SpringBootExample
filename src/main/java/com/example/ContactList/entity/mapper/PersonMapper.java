package com.example.ContactList.entity.mapper;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@RequiredArgsConstructor
public class PersonMapper {

    public static Person mapPerson(User user) {

        return Person.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contacts(mapContacts(user))
                .build();
    }

    public static Set<Contact> mapContacts(User user) {
        Set<Contact> contacts = new HashSet<>();
        if(user.getContactLists() == null)
            return new HashSet<>();

        for (ContactList c : user.getContactLists()) {
            contacts.add(Contact.builder()
                    .contact(c.getContact())
                    .type(c.getType())
                    .contactName(c.getContactName())
                    .build());
        }

        return contacts;
    }
}
