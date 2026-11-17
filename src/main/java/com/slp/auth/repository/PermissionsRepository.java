package com.slp.auth.repository;

import com.slp.auth.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "permissions")
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
}
