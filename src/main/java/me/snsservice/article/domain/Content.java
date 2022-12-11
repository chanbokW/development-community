package me.snsservice.article.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    private final static int MIN_LENGTH = 2;
    private final static int MAX_LENGTH = 500;

    @Column(nullable = false)
    private String content;

    public Content(String content) {
        validateContentNullOrBlank(content);
        validateContentLength(content);
        this.content = content;
    }

    public String value() {
        return content;
    }

    private void validateContentNullOrBlank(String content) {
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_ARTICLE_CONTENT);
        }
    }

    private void validateContentLength(String content) {
        int length = content.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new BusinessException(ErrorCode.INVALID_ARTICLE_CONTENT);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return value().equals(content.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
