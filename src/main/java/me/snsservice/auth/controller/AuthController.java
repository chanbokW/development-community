package me.snsservice.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.dto.AccessTokenRequest;
import me.snsservice.auth.dto.AccessTokenResponse;
import me.snsservice.auth.dto.LoginRequest;
import me.snsservice.auth.dto.TokenResponse;
import me.snsservice.auth.service.AuthService;
import me.snsservice.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public CommonResponse<TokenResponse> signup(@RequestBody LoginRequest loginRequest) {
        return CommonResponse.res(HttpStatus.OK, "로그인 후 토큰 발급", authService.login(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/reissue")
    public CommonResponse<AccessTokenResponse> reissue(@RequestBody AccessTokenRequest accessTokenRequest) {
        return CommonResponse.res(HttpStatus.OK, "accssToken 재발급", authService.reissue(accessTokenRequest.getRefreshToken()));
    }

}
