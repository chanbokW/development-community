package me.snsservice.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.domain.CustomUserDetails;
import me.snsservice.auth.dto.AccessTokenResponse;
import me.snsservice.auth.dto.TokenResponse;
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
    public TokenResponse login(String email, String password) {
        Authentication authentication = getAuthentication(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return createToken(customUserDetails);
    }

    private String createAccessToken(Long id, String email, Role role) {
        return jwtTokenProvider.createAccessToken(id, email, role);
    }

    private String createRefreshToken(Long id, String email, Role role) {
        return jwtTokenProvider.createRefreshToken(id, email, role);
    }

    private TokenResponse createToken(CustomUserDetails customUserDetails) {
        Long id = customUserDetails.getId();
        String email = customUserDetails.getEmail();
        Role role = getRole(customUserDetails);
        return new TokenResponse(createAccessToken(id, email, role), createRefreshToken(id, email, role));
    }

    private Role getRole(CustomUserDetails customUserDetails) {
        return customUserDetails.getAuthorities().stream()
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

    public AccessTokenResponse reissue(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return new AccessTokenResponse(
                createAccessToken(customUserDetails.getId(), customUserDetails.getEmail(), getRole(customUserDetails))
        );
    }
}
