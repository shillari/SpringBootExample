package com.example.ContactList.entity.mapper;

import com.example.ContactList.entity.Contact;
import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ContactListMapper {

    public static ContactList mapContact(Contact contact, User user) {
        return ContactList.builder()
                .user(user)
                .contactName(contact.getContactName())
                .contactEmail(contact.getContactEmail())
                .phoneOne(contact.getPhoneOne())
                .phoneTwo(contact.getPhoneTwo())
                .contactId(contact.getContactId())
                .build();
    }

    public static List<Contact> mapAllContacts(List<ContactList> sContactList) {
        List<Contact> contacts = new ArrayList<>();
        for (ContactList cl : sContactList) {
            contacts.add(Contact.builder()
                    .contactName(cl.getContactName())
                    .contactEmail(cl.getContactEmail())
                    .phoneOne(cl.getPhoneOne())
                    .phoneTwo(cl.getPhoneTwo())
                    .contactId(cl.getContactId())
                    .build());
        }
        return contacts;
    }

    public static Contact mapUContact(ContactList cl) {
        return Contact.builder()
                .contactName(cl.getContactName())
                .contactEmail(cl.getContactEmail())
                .phoneOne(cl.getPhoneOne())
                .phoneTwo(cl.getPhoneTwo())
                .contactId(cl.getContactId())
                .build();
    }
}
