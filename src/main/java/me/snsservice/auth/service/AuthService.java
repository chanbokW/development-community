package me.snsservice.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.domain.CustomUserDetails;
import me.snsservice.auth.dto.TokenDto;
import me.snsservice.member.domain.Role;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public TokenDto login(String email, String password) {
        Authentication authentication = getAuthentication(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        TokenDto tokenDto = createToken(customUserDetails.getId(), customUserDetails.getEmail(), authorities(authentication));
        return tokenDto;
    }

    private TokenDto createToken(Long id, String email, Role role) {
        String accessToken = jwtTokenProvider.createAccessToken(id, email, role);
        String refreshToken = jwtTokenProvider.createRefreshToken(id, email, role);
        return new TokenDto(accessToken, refreshToken);
    }

    private Role authorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .map(Role::valueOf)
                .orElseThrow(() -> new IllegalArgumentException("접근 권한이 없습니다."));
    }


    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
