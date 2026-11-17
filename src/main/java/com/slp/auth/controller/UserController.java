package com.slp.auth.controller;

import com.slp.auth.records.UserRecord;
import com.slp.auth.entity.Users;
import com.slp.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody final UserRecord user){
        Users createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

}
