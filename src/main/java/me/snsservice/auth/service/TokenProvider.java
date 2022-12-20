package me.snsservice.auth.service;


import me.snsservice.member.domain.Role;
import org.springframework.security.core.Authentication;

public interface TokenProvider <T> {

    String createAccessToken(Long memberId, String email, Role role);

    String createRefreshToken(Long memberId, String email, Role role);

    T getClaims(String token);

    Long getMemberId(String token);

    String getEmail(String token);

    boolean validateToken(String token);

    Authentication getAuthentication(String token);
}
