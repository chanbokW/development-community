package me.snsservice.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.common.jwt.JwtTokenProvider;
import me.snsservice.common.jwt.dto.TokenDto;
import me.snsservice.common.jwt.util.AuthenticationManagement;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagement authenticationManagement;

    public TokenDto login(String email, String password) {
        Authentication authentication = authenticationManagement.createAuthentication(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.createToken(authentication);
    }
}
