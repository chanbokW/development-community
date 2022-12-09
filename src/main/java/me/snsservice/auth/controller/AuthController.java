package me.snsservice.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.dto.LoginRequest;
import me.snsservice.auth.service.LoginService;
import me.snsservice.common.jwt.dto.TokenDto;
import me.snsservice.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
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
    public CommonResponse<TokenDto> signup(@RequestBody LoginRequest loginRequest) {
        return CommonResponse.res(HttpStatus.OK, "로그인 후 토큰 발급", loginService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }
}
