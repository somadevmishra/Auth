package com.slp.auth.model;

import com.slp.auth.entity.Roles;
import com.slp.auth.entity.Users;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class UserPrincipal implements UserDetails {

    private final Users users;

    public UserPrincipal(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return List.of(new SimpleGrantedAuthority("USER"));
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return "{bcrypt}"+users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUserName();
    }
}
