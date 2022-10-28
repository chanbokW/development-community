package me.snsservice.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private final static Pattern PATTERN =
        Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");

    @Column(name = "email", nullable = false)
    private String email;

    public Email(String email) {
        validateEmailPattern(email);
        this.email = email;
    }

    public String value() {
        return email;
    }

    private void validateEmailPattern(String email) {
        if (isNotValid(email)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_EMAIL);
        }
    }

    private boolean isNotValid(String email) {
        return !PATTERN.matcher(email).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return value().equals(email.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
