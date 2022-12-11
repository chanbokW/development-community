package me.snsservice.article.domain;

import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class ArticleTest {

    private Member member;

    @BeforeEach
    void init() {
        member = Member.builder()
                .nickname("helloworld")
                .password("test1234!")
                .email("email1234@test.com")
                .build();
    }

    @Test
    @DisplayName("Article 생성 성공")
    void createArticleTest() {
        Article article = Article.builder()
                .title("title")
                .content("content")
                .member(member)
                .build();

        assertThat(article.getTitle()).isEqualTo("title");
        assertThat(article.getContent()).isEqualTo("content");
        assertThat(article.getMember()).isEqualTo(member);
    }

    @DisplayName("게시물을 등록 할 때 제목을 등록할 때 길이를 초과하면 예외가 발생한다. ")
    @ParameterizedTest
    @ValueSource(strings = {"아", "springDataJPAspringDataJPAspringDataJPAspringData51",})
    void titleExceptionLengthTest(String value) {
        assertThatThrownBy(() -> Article.builder()
                .title(value)
                .content("content")
                .member(member)
                .build())
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_TITLE.getMessage());
    }

    @DisplayName("게시물을 등록 할 때 제목을 등록할 때 Null일 경우 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void titleExceptionNullCheckTest(String nullValue) {
        assertThatThrownBy(() -> Article.builder()
                .title(nullValue)
                .content("content")
                .member(member)
                .build())
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_TITLE.getMessage());
    }

    @DisplayName("게시물을 등록 할 때 본문을 입력할 때 null 일경우 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void contentExceptionNullTest(String nullValue) {
        assertThatThrownBy(() -> Article.builder()
                .title("title")
                .content(nullValue)
                .member(member)
                .build())
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_CONTENT.getMessage());
    }

    @DisplayName("게시물의 등록 할 때 본문을 입력 할 때 빈 문자열이거나 제한된 길이를 초과하거나 미만일 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "오"})
    void contentExceptionLengthOrBlankTest(String value) {
        assertThatThrownBy(() -> Article.builder()
                .title("title")
                .content(value)
                .member(member)
                .build())
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_ARTICLE_CONTENT.getMessage());
    }
}