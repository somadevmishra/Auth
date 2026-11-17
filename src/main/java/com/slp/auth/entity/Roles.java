package com.slp.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_name", unique = true)
    private String roleName;

    @Column(name = "status")
    private Boolean active;

    @Column(name = "description")
    private String description;

}
