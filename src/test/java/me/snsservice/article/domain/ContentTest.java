package me.snsservice.article.domain;

import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ContentTest {

    @DisplayName("게시물의 본문을 입력하면 Content가 리턴된다")
    @ParameterizedTest
    @ValueSource(strings = {"객체지향은 ~~!~~", "TDD란 ~~~", "테스트코드 작성"})
    void contentSuccessTest(String value) {
        Content content = new Content(value);
        assertThat(content.value()).isEqualTo(value);
    }

    @DisplayName("게시물의 본문을 입력할 때 null 일경우 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void contentExceptionNullTest(String nullValue) {
        assertThatThrownBy(() -> new Content(nullValue))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_CONTENT.getMessage());
    }

    @DisplayName("게시물의 본문을 입력 할 때 빈 문자열이거나 제한된 길이를 초과하거나 미만일 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "오"})
    void contentExceptionLengthOrBlankTest(String value) {
        assertThatThrownBy(() -> new Content(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_CONTENT.getMessage());
    }

    @Test
    @DisplayName("동일한 게시물의 본문을 입력할 경우 true를 리턴한다")
    void contentEqualsTest() {
        Content content = new Content("테스트코드 작성");
        assertThat(content).isEqualTo(new Content("테스트코드 작성"));
    }
}