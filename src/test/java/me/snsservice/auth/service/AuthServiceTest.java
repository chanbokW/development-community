package me.snsservice.auth.service;

import me.snsservice.auth.dto.AccessTokenResponse;
import me.snsservice.auth.dto.TokenResponse;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import me.snsservice.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static me.snsservice.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void init() {
        Member member = Member.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .nickname(TEST_NICKNAME)
                .role(Role.ROLE_MEMBER)
                .build()
                .passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }

    @Test
    @DisplayName("로그인 성공 시 accssToken과 refreshToken을 발급한다")
    void getLoginAccessTokenAndRefreshTokenTest() {
        TokenResponse tokenResponse = authService.login(TEST_EMAIL, TEST_PASSWORD);
        assertThat(tokenResponse.getAccessToken()).isNotEmpty();
        assertThat(tokenResponse.getRefreshToken()).isNotEmpty();
    }

    @Test
    @DisplayName("가입 되지 않은 이메일로 로그인 할 시 예외가 발생한다.")
    void loginUnsubscribedEmailExceptionTest() {
        assertThatThrownBy(() -> authService.login("test123432@test.com", TEST_PASSWORD));
    }

    @Test
    @DisplayName("가입 되지 않은 이메일로 로그인 할 시 예외가 발생한다.")
    void loginPasswordMissMatchExceptionTest() {
        assertThatThrownBy(() -> authService.login(TEST_EMAIL, "cbasdfq123!"));
    }

    @Test
    @DisplayName("refresh 으로 accessToken 발급받을 수 있다.")
    void reIssueAccessTokenTest() {
        String refreshToken = jwtTokenProvider.createRefreshToken(1L, "helloWorld", Role.ROLE_MEMBER);
        AccessTokenResponse reissueAccessToken = authService.reissue(refreshToken);
        assertThat(reissueAccessToken.getAccessToken()).isNotEmpty();
    }

    @Test
    @DisplayName("잘못된 refreshToken으로 발급할 시 예외가 발생한다.")
    void invalidRefreshTokenValueExceptionTest() {
        String refreshToken = "aaaaaaa.adddddddda.weqasdasfd";
        assertThatThrownBy(() -> authService.reissue(refreshToken))
                .isInstanceOf(BusinessException.class);
    }
}