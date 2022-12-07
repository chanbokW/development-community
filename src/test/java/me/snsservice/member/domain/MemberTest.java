package me.snsservice.member.domain;

import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @Test
    @DisplayName("회원 객체 생성 성공")
    void createMemberTest() {
        String nickname = "codeMonster";
        String email = "test1234@test.com";
        String password = "password123!";

        Member member = Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getPassword()).isEqualTo(password);

    }


    @DisplayName("이메일 형식에 맞지 않을 시 예외 발생 한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "korea.com", "id.korea.com", "id@korea..com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"})
    void emailPatterMatchExceptionTest(String value) {

        String nickname = "codeMonster";
        String password = "password123!";

        assertThatThrownBy(() -> Member.builder()
                .email(value)
                .password(password)
                .nickname(nickname)
                .build()
        )
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_MEMBER_EMAIL.getMessage());
    }

    @DisplayName("비밀번호 형식에 맞지 않을 시 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "12312123", "!!hellohi", "!!Password12345678910"})
    void passwordPatterMatchExceptionTest(String value) {

        String nickname = "codeMonster";
        String email = "test1234@test.com";

        assertThatThrownBy(() -> Member.builder()
                .email(email)
                .password(value)
                .nickname(nickname)
                .build()
        )
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_MEMBER_PASSWORD.getMessage());
    }

    @DisplayName("닉네임 형식에 맞지 않을 시 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "ㅁㄴㅇㄹ", "arsㄱㅏ","가","1230120adfsfsd가나다라마바사아자차"})
    void nicknamePatterMatchExceptionTest(String value) {
        String email = "test1234@test.com";
        String password = "password123!";

        assertThatThrownBy(() -> Member.builder()
                .email(email)
                .password(password)
                .nickname(value)
                .build()
        )
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_MEMBER_NICKNAME.getMessage());
    }
}