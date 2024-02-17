package com.example.ContactList.entity.mapper;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ContactListMapper {

    public static ContactList mapContact(Contact contact, User user) {
        return ContactList.builder()
                .user(user)
                .contact(contact.getContact())
                .type(contact.getType())
                .contactName(contact.getContactName())
                .build();
    }

    public static Set<Contact> mapAllContacts(List<ContactList> sContactList) {
        Set<Contact> contacts = new HashSet<>();
        for (ContactList cl : sContactList) {
            contacts.add(Contact.builder()
                    .contact(cl.getContact())
                    .type(cl.getType())
                    .contactName(cl.getContactName())
                    .build());
        }
        return contacts;
    }
}
