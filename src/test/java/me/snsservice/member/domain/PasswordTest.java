package me.snsservice.member.domain;

import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @DisplayName("패스워드는 8자이상 20자 이하이며, 영어 대소문자, 숫자, 특수문자가 포함되어 있어야한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "12312123", "!!hellohi", "!!Password12345678910"})
    void passwordExceptionFormatTest(String value) {
        PasswordEncoder passwordEncoder = TestPasswordEncoder.initialize();
        assertThatThrownBy(() -> Password.encrypt(value, passwordEncoder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_MEMBER_PASSWORD.getMessage());
    }

    static class TestPasswordEncoder implements PasswordEncoder {
        private static final int SALT = 4;

        @Override
        public String encode(CharSequence rawPassword) {
            return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(SALT));
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
        }

        public static PasswordEncoder initialize() {
            return new TestPasswordEncoder();
        }
    }
}