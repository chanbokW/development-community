package me.snsservice.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.snsservice.auth.domain.CustomUserDetails;
import me.snsservice.member.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static me.snsservice.auth.domain.JwtExpiration.ACCESS_TOKEN_EXPIRED_TIME;
import static me.snsservice.auth.domain.JwtExpiration.REFRESH_TOKEN_EXPIRED_TIME;

@Slf4j
@Component
public class JwtTokenProvider implements TokenProvider<Claims> {

    private static final String AUTHORITIES_KEY = "role";
    private static final String MEMBER_ID = "memberId";
    private final String secret;
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public String createAccessToken(Long memberId, String email, Role role) {
        return createToken(memberId, email, role, ACCESS_TOKEN_EXPIRED_TIME.getTime());
    }

    @Override
    public String createRefreshToken(Long memberId, String email, Role role) {
        return createToken(memberId, email, role, REFRESH_TOKEN_EXPIRED_TIME.getTime());
    }

    private String createToken(Long memberId, String email, Role role, Long expiredTime) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, role.toString())
                .claim(MEMBER_ID, memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // TODO Exception 처리
    @Override
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("만료된 Jwt 토큰입니다.");
            return e.getClaims();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }
    }

    @Override
    public Long getMemberId(String token) {
        return getClaims(token).get(MEMBER_ID, Long.class);
    }

    @Override
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    // TODO Exception 처리
    @Override
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String role = String.valueOf(claims.get(AUTHORITIES_KEY));
        String email = claims.getSubject();
        Long id = claims.get(MEMBER_ID, Long.class);
        CustomUserDetails customUserDetails = new CustomUserDetails(id, email, role);
        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
    }
}
