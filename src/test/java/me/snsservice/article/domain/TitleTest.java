package me.snsservice.article.domain;

import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TitleTest {

    @DisplayName("게시물의 제목을 등록한다")
    @ParameterizedTest
    @ValueSource(strings = {"안녕하세요.", "JVM에 대해", "Java에 대해"})
    void titleSuccessTest(String value) {
        Title title = new Title(value);
        assertThat(title.value()).isEqualTo(value);
    }

    @DisplayName("게시물의 제목을 등록할 때 빈 문자열이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "",})
    void titleExceptionBlankTest(String value) {
        assertThatThrownBy(() -> new Title(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_TITLE.getMessage());
    }

    @DisplayName("게시물의 제목을 등록할 때 빈 문자열이면 예외가 발생한다. ")
    @ParameterizedTest
    @ValueSource(strings = {"아", "springDataJPAspringDataJPAspringDataJPAspringData51",})
    void titleExceptionLengthTest(String value) {
        assertThatThrownBy(() -> new Title(value))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_TITLE.getMessage());
    }

    @DisplayName("게시물의 제목을 등록할 때 Null일 경우 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void titleExceptionNullCheckTest(String nullValue) {
        assertThatThrownBy(() -> new Title(nullValue))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_TITLE.getMessage());
    }

    @Test
    @DisplayName("동일 한 게시물의 제목을 비교하면 true를 리턴한다")
    void titleEqualsTest() {
        Title title = new Title("Config");
        assertThat(title).isEqualTo(new Title("Config"));
    }
}