package com.slp.auth.config;

import com.slp.auth.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 1. The default username is "user" and uuid password is given in the logs
 * 2. to implement a basic default user, one can set the spring.security.user.name, spring.security.user.password
 *    in the application.properties/yaml file
 * 3. once this class is overridden, it avoids the username passwords
 * 4. csrf is disabled; csrf is disabled when we need the credentials in all the request. basically it is used in
 *    STATELESS sessions
 * 5. httpBasic is used to pass the basic auth from postman/bruno
 * 6. sessionmanagment to maintain the session creation policy
 * Authentication provider is interface which provides an providers for authentication
 * There several types of Auth-provider implementation. One of them in implemented here
 *
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final MyUserDetailsService userDetailsService;

    private static final String[] AUTH_EXCPETION_LIST = {
            "/",
            "/login",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationConverter jwtAuthenticationConverter){

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(AUTH_EXCPETION_LIST).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                ))
                .build()
                ;
    }

    @Bean
    public AuthenticationProvider getAuthProvider(){
        return new DaoAuthenticationProvider(userDetailsService);
    }

    @Bean
    public AuthenticationManager getAuthManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }


}
