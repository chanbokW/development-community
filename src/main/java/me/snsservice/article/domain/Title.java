package me.snsservice.article.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    private final static int MIN_LENGTH = 2;
    private final static int MAX_LENGTH = 50;

    @Column(length = MAX_LENGTH, nullable = false)
    private String title;

    public Title(String title) {
        validateTitleNullOrBlank(title);
        validateTitleLength(title);
        this.title = title;
    }

    public String value() {
        return title;
    }

    private void validateTitleNullOrBlank(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_ARTICLE_TITLE);
        }
    }

    private void validateTitleLength(String title) {
        int length = title.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new BusinessException(ErrorCode.INVALID_ARTICLE_TITLE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title = (Title) o;
        return value().equals(title.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
