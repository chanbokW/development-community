package me.snsservice.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.dto.LoginRequest;
import me.snsservice.auth.service.LoginService;
import me.snsservice.common.jwt.dto.TokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> signup(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> signout() {
        return ResponseEntity.ok().body("Hello");
    }
}
