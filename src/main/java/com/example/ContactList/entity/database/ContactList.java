package com.example.ContactList.entity.database;

import com.example.ContactList.entity.EnumContactTypes;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="contact_list", uniqueConstraints = @UniqueConstraint(columnNames = {"contact_name, user_id"}))
public class ContactList {

    @Id
    @GeneratedValue
    @Column(name = "contact_id")
    private Integer contactId;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(name="contact_email")
    private String contactEmail;
    @Column(name="phone_one")
    private String phoneOne;
    @Column(name="phone_two")
    private String phoneTwo;
    @Column(name="contact_name", unique = true)
    private String contactName;
}
