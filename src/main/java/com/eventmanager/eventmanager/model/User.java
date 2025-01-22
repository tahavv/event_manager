package com.eventmanager.eventmanager.model;

import com.eventmanager.eventmanager.model.enmus.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "login_attempts", nullable = false)
    private int loginAttempts = 0;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked = false;

    @Column(name = "verified", nullable = false)
    private boolean verified = false;

    @OneToMany(mappedBy = "organizer")
    @JsonManagedReference(value = "user-organized-events")
    @JsonIgnore
    private List<Event> organizedEvents;

    @ManyToMany(mappedBy = "participants")
    @JsonBackReference(value = "event-participants")
    @JsonIgnore
    private List<Event> events;
}
