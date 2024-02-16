package com.example.ContactList.repository;

import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ContactListRepository extends JpaRepository<ContactList, Integer> {

    @Query("SELECT c FROM ContactList c WHERE c.user = ?1 AND c.contact = ?2")
    ContactList findContactList(User user, String contact);
//
//    @Query("INSERT INTO ContactList (user,contact,type,contactName) VALUES (?1,?2,?3,?4)")
//    int insertContact(int userId, String contact, String type, String contactName);
//
    Set<ContactList> findAllByUser(User user);
}
