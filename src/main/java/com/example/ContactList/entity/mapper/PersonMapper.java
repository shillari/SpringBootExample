package com.example.ContactList.entity.mapper;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.Person;
import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
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

    public static List<Contact> mapContacts(User user) {
        List<Contact> contacts = new ArrayList<>();
        if(user.getContactLists() == null)
            return new ArrayList<>();

        for (ContactList c : user.getContactLists()) {
            contacts.add(Contact.builder()
                    .contactName(c.getContactName())
                    .contactEmail(c.getContactEmail())
                    .phoneOne(c.getPhoneOne())
                    .phoneTwo(c.getPhoneTwo())
                    .build());
        }

        return contacts;
    }
}
