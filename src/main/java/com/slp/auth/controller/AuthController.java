package com.slp.auth.controller;

import com.slp.auth.records.AuthResponse;
import com.slp.auth.records.LoginRecord;
import com.slp.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/")
    public String defaultRequest(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        return """ 
                Hello Auth Service
                sessionId: %s
                """.formatted(sessionId);
    }

    /**
     * this is an old API to test the automatic login
     * @param loginModel
     * @return
     */
    @Deprecated
    public String login(@RequestBody @Valid LoginRecord loginModel){
        return userService.verifyLoginCredentials(loginModel);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginAndToken(@RequestBody @Valid LoginRecord loginRecord){
        AuthResponse authResponse = userService.verifyLoginAndGenrateToken(loginRecord);
        return ResponseEntity.ok(authResponse);
    }
}
