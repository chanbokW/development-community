package me.snsservice.member.domain;

import me.snsservice.common.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NicknameTest {

    @DisplayName("파라미터의 value 호출하면 이메일이 리턴된다.")
    @ParameterizedTest
    @ValueSource(strings = {"안녕", "Hello123"})
    void nicknameTest(String value) {
        Nickname nickname = new Nickname(value);
        assertThat(nickname.value()).isEqualTo(value);
    }

    @DisplayName("닉네임은 숫자와 영문, 한글음절을 포함한 2자 이상 20자가 아니면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ㅁㄴㅇㄹ", "arsㄱㅏ","가","1230120adfsfsd가나다라마바사아자차"})
    void nicknameExceptionFormatTest(String value) {
        assertThatThrownBy(() -> new Nickname(value))
                .hasMessage(ErrorCode.INVALID_MEMBER_NICKNAME.getMessage());
    }

    @DisplayName("닉네임이 Null일 경우 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void nicknameExceptionNullTest(String nullValue) {
        assertThatThrownBy(() -> new Nickname(nullValue))
                .hasMessage(ErrorCode.NOT_INPUT_MEMBER_NICKNAME.getMessage());
    }

    @Test
    @DisplayName("동일한 닉네임을 입력할 경우 true를 반환한다")
    void nicknameEqualsTest() {
        Nickname nickname = new Nickname("하이");
        assertThat(nickname).isEqualTo(new Nickname("하이"));
    }

    @Test
    @DisplayName("동일하지 닉네임을 입력할 경우 false를 반환한다")
    void nicknameNotEqualsTest() {
        Nickname nickname = new Nickname("하이");
        assertThat(nickname).isNotEqualTo(new Nickname("하이1"));
    }
}