package com.slp.auth.service;

import com.slp.auth.entity.Users;
import com.slp.auth.model.UserPrincipal;
import com.slp.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public MyUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user =  usersRepository
            .findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username"+ username))
            ;
    log.info("User found with username: {}", user.getUserName());
        return new UserPrincipal(user);
    }
}
