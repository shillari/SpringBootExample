package com.example.ContactList.repository;

import com.example.ContactList.entity.database.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactListRepository extends JpaRepository<ContactList, Integer> {

    @Query("SELECT c FROM ContactList c WHERE c.user= ?1 AND c.contact = ?2")
    ContactList findContactList(int userId, String contact);
}
