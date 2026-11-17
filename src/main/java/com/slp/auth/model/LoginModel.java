package com.slp.auth.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginModel {

    @NotNull( message = "User Name cannot be blank")
    private String userName;

    @NotNull (message = "Password cannot be blank")
    private String password;
}
