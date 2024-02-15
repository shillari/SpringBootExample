package com.example.ContactList.repository;

import com.example.ContactList.entity.database.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContactListRepository extends JpaRepository<ContactList, Integer> {

    @Query("SELECT c FROM contact_list c WHERE c.user_id= ?1 AND c.contact = ?2")
    Optional<ContactList> findContactList(int userId, String contact);
}
