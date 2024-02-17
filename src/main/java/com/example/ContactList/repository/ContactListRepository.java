package com.example.ContactList.repository;

import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactListRepository extends JpaRepository<ContactList, Integer> {

    @Query("SELECT c FROM ContactList c WHERE c.user = ?1 AND c.contactName = ?2")
    ContactList findContactList(User user, String contactName);
//
//    @Query("INSERT INTO ContactList (user,contact,type,contactName) VALUES (?1,?2,?3,?4)")
//    int insertContact(int userId, String contact, String type, String contactName);
//
    List<ContactList> findAllByUser(User user);

    @Modifying
    @Query("DELETE FROM ContactList c WHERE c.contactId = :contactId")
    void deleteContactById(int contactId);
}
