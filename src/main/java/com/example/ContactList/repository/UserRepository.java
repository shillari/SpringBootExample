package com.example.ContactList.repository;

import com.example.ContactList.entity.database.ContactList;
import com.example.ContactList.entity.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.contactLists WHERE u.email = :email")
    Optional<User> findByEmailWithContactLists(String email);

    @Query("SELECT u.contactLists from User u where u.email = :email")
    Set<ContactList> findAllContactLists(String email);

}
