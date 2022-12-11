package me.snsservice.member.domain;

import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {

    @DisplayName("파라미터의 value 호출하면 이메일이 리턴된다.")
    @ParameterizedTest
    @ValueSource(strings = {"test1234@email.com", "test@email.com"})
    void emailSuccessTest(String value) {
        Email email = new Email(value);
        assertThat(email.value()).isEqualTo(value);
    }

    @DisplayName("올바르지 않은 형식의 이메일일 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "korea.com", "id.korea.com", "id@korea..com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"})
    void emailExceptionFormatTest(String value) {
        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_MEMBER_EMAIL.getMessage());
    }

    @Test
    @DisplayName("동일한 이메일 일 경우 true를 반환한다")
    void emailEqualsTest() {
        Email email = new Email("hello1234@example.com");
        assertThat(email).isEqualTo(new Email("hello1234@example.com"));
    }

    @Test
    @DisplayName("동일하지 않은 이메일 일 경우 false를 반환한다")
    void emailNotEqualsTest() {
        Email email = new Email("hello1234@example.com");
        assertThat(email).isNotEqualTo(new Email("spring1234@example.com"));
    }
}
