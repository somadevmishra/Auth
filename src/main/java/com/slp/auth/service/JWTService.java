package com.slp.auth.service;

import com.slp.auth.entity.Roles;
import com.slp.auth.entity.Users;
import com.slp.auth.model.UserPrincipal;
import com.slp.auth.repository.UserRoleRepository;
import com.slp.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {

    private final UsersRepository usersRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtEncoder jwtEncoder;

    @Value("${app.security.jwt.expiration}")
    private Long expiration;

    public String generateToken(String userName){
        Instant now = Instant.now();
        Users user = usersRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(" User name not found"+userName));

        List<Roles> roles = userRoleRepository.findRolesByUserId(user.getId());
        List<String> roleNames = roles.stream().filter( role -> role.getActive().equals(Boolean.TRUE))
                .map(Roles::getRoleName).toList();
        log.info("User: {} has following roles: {}", user.getUserName(), roleNames);

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("auth-serv")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .claim("roles", roleNames)
                .subject(userName)
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }

    public String generateToken(Authentication authentication){
        Instant now = Instant.now();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        assert userPrincipal != null;
        List<String> roles = getRoles(userPrincipal);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    private List<String> getRoles(UserPrincipal userPrincipal){

        Users user = usersRepository.findByUserName(userPrincipal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(" User name not found"+userPrincipal.getUsername()));

        List<Roles> roles = userRoleRepository.findRolesByUserId(user.getId());
        return roles.stream().filter( role -> role.getActive().equals(Boolean.TRUE))
                .map(rol -> "ROLE_"+rol.getRoleName()).toList();
    }
}
