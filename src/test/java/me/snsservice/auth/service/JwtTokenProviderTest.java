package me.snsservice.auth.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private final String SECRET = "abcd".repeat(32);
    private final String TEST_EMAIL = "test1234@test.com";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET);

    @Test
    @DisplayName("AccessToken을 생성한다.")
    void createAccessTokenTest() {
        String accessToken = jwtTokenProvider.createAccessToken(1L, TEST_EMAIL, Role.ROLE_MEMBER);
        assertThat(accessToken.split("\\.")).hasSize(3);
    }

    @Test
    @DisplayName("RefreshToken 을 생성한다.")
    void createRefreshTokenTest() {
        String refreshToken = jwtTokenProvider.createRefreshToken(1L, TEST_EMAIL, Role.ROLE_ADMIN);
        assertThat(refreshToken.split("\\.")).hasSize(3);

    }

    @Test
    @DisplayName("토큰 정보로 아이디와 이메일을 가져온다")
    void getEmailTest() {
        String accessToken = jwtTokenProvider.createAccessToken(1L, TEST_EMAIL, Role.ROLE_MEMBER);
        String email = jwtTokenProvider.getEmail(accessToken);
        assertThat(email).isEqualTo(TEST_EMAIL);
    }

    @Test
    @DisplayName("AccessToken이 정보가 만료 후 토큰 정보를 검증 시 예외가 발생한다.")
    void accessTokenValidateExceptionTest() {
        StubTokenProvider stubTokenProvider = new StubTokenProvider(SECRET, 0L, 0L);
        String accessToken = stubTokenProvider.createAccessToken(1L, TEST_EMAIL, Role.ROLE_MEMBER);

        assertThatThrownBy(() -> stubTokenProvider.validateToken(accessToken))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("유효하지 않은 토큰을 검즐할 시 예외가 발생한다.")
    void InvalidTokenValidateExceptionTest() {
        String token = "asdlfjadslfjsadlf.sdlfjsaldfjqo.sfdjlajflsd";
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(token))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("RefreshToken이 정보가 만료 후 토큰 정보를 검증 시 예외가 발생한다.")
    void refreshTokenValidateExceptionTest() {
        StubTokenProvider stubTokenProvider = new StubTokenProvider(SECRET, 0L, 0L);
        String refreshToken = stubTokenProvider.createRefreshToken(1L, TEST_EMAIL, Role.ROLE_MEMBER);

        assertThatThrownBy(() -> stubTokenProvider.validateToken(refreshToken))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("유효하지 않은 토큰으로 claims를 가져올 시 예외가 발생한다.")
    void InvalidTokenClaimExceptionTest() {
        StubTokenProvider stubTokenProvider = new StubTokenProvider(SECRET, 0L, 0L);
        String token = "asdlfjadslfjsadlf.sdlfjsaldfjqo.sfdjlajflsd";
        assertThatThrownBy(() -> stubTokenProvider.getClaims(token))
                .isInstanceOf(MalformedJwtException.class);
    }

    @Test
    @DisplayName("만료된 AccessToken 토큰으로 아이디와 이메일을 가져올 시 ExpiredJwtException이 발생한다.")
    void accessTokenGetEmailExpiredJwtException() {
        StubTokenProvider stubTokenProvider = new StubTokenProvider(SECRET, 0L, 0L);
        String accessToken = stubTokenProvider.createAccessToken(1L, TEST_EMAIL, Role.ROLE_MEMBER);

        assertThatThrownBy(() -> stubTokenProvider.getEmail(accessToken))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    @DisplayName("만료된 RefreshToken 토큰으로 아이디와 이메일을 가져올 시 ExpiredJwtException이 발생한다.")
    void refreshTokenGetEmailExpiredJwtException() {
        StubTokenProvider stubTokenProvider = new StubTokenProvider(SECRET, 0L, 0L);
        String refreshToken = stubTokenProvider.createRefreshToken(1L, TEST_EMAIL, Role.ROLE_MEMBER);

        assertThatThrownBy(() -> stubTokenProvider.getEmail(refreshToken))
                .isInstanceOf(ExpiredJwtException.class);
    }
}