package me.snsservice.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.snsservice.auth.domain.CustomUserDetails;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.common.exception.ErrorCode;
import me.snsservice.member.domain.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;

/**
 * Test용도 TokenProvider
 */
public class StubTokenProvider implements TokenProvider<Claims> {

    private static final String AUTHORITIES_KEY = "role";
    private static final String MEMBER_ID = "memberId";
    private final String secret;
    private final Key key;
    private final long accessTokenExpiredTime;
    private final long refreshTokenExpiredTime;

    public StubTokenProvider(String secret, long accessTokenExpiredTime, long refreshTokenExpiredTime) {
        this.secret = secret;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public String createAccessToken(Long memberId, String email, Role role) {
        return createToken(memberId, email, role, accessTokenExpiredTime);
    }

    @Override
    public String createRefreshToken(Long memberId, String email, Role role) {
        return createToken(memberId, email, role, refreshTokenExpiredTime);
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

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
            throw new BusinessException(ErrorCode.INVALID_TOKEN_VALUE);
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
