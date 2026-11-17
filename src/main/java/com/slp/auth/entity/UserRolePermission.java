package com.slp.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_role_permissions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId, roleId, permissionId"})
})
@Data
public class UserRolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "assigned_by")
    private Long assignedBy;
}
