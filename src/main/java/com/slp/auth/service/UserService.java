package com.slp.auth.service;

import com.slp.auth.records.AuthResponse;
import com.slp.auth.records.LoginRecord;
import com.slp.auth.records.UserRecord;
import com.slp.auth.entity.Users;
import com.slp.auth.model.LoginModel;
import com.slp.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Value("${app.security.jwt.expiration}")
    private long expiration;

    public Users createUser(UserRecord userRecord){
        Users user = new Users();
        BeanUtils.copyProperties(userRecord, user);
        user.setPassword(new BCryptPasswordEncoder(12).encode(userRecord.password()));
        return usersRepository.save(user);
    }

    public Users getUserByUserName(String userName){
        return usersRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found" + userName) );
    }

    public String verifyLoginCredentials(LoginRecord loginRecord){
        LoginModel loginModel = new LoginModel(loginRecord.userName(), loginRecord.password());
        Authentication authentication =
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getUserName(), loginModel.getPassword()));

        return authentication.isAuthenticated() ? "success": "failure";
    }

    public AuthResponse verifyLoginAndGenrateToken(LoginRecord loginRecord){
        LoginModel loginModel = new LoginModel(loginRecord.userName(), loginRecord.password());
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginModel.getUserName(), loginModel.getPassword()));
        String accessToken = jwtService.generateToken(authentication);
        return  new AuthResponse(accessToken, "Bearer", expiration/1000);
    }
}
