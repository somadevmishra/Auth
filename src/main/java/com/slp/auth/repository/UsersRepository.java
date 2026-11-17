package com.slp.auth.repository;

import com.slp.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "users")
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String userName);
}
