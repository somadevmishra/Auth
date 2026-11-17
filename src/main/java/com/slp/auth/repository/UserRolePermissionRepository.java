package com.slp.auth.repository;

import com.slp.auth.entity.UserRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "userrolepermission")
public interface UserRolePermissionRepository extends JpaRepository<UserRolePermission, Long> {
}
