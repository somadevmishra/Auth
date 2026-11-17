package com.slp.auth.repository;

import com.slp.auth.entity.Roles;
import com.slp.auth.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "userroles")
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {

    @RestResource(path = "roles")
    @Query("select u.roles from UserRoles u where u.users.id = :userId")
    List<Roles> findRolesByUserId(Long userId);
}
