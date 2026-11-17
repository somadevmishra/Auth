package com.slp.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Table
@Data
@Component
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false, unique = true)
    @NotNull(message = "user name cannot be null")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "email_id", unique = true)
    private String emailId;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "salt")
    private String salt;
}

