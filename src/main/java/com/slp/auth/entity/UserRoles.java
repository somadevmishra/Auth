package com.slp.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "user_roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "roleId"})
})
@Data
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_usr_role_users")
    )
    private Users users;

    @ManyToOne
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_usr_role_roles")
    )
    private Roles roles;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "assigned_by")
    private Long assignedBy;
}
