package me.snsservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @Email
    private String email;
    private String password;
}
