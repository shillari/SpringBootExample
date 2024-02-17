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
@Table(name="contact_list", uniqueConstraints = @UniqueConstraint(columnNames = {"contact, user_id"}))
public class ContactList {

    @Id
    @GeneratedValue
    @Column(name = "contact_id")
    private Integer contactId;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(name="contact", unique = true)
    private String contact;
    @Enumerated(EnumType.STRING)
    private EnumContactTypes type;
    @Column(name="contact_name")
    private String contactName;
}
