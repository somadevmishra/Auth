package com.slp.auth.repository;

import com.slp.auth.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "roles")
public interface RolesRepository extends JpaRepository<Roles, Long> {
}
