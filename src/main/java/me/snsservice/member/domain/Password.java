package me.snsservice.member.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private static final Pattern PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$");

    @Column(name = "password")
    private String password;

    public static Password encrypt(String password, PasswordEncoder encoder) {
        validatePasswordPattern(password);
        return new Password(encoder.encode(password));
    }

    public static Password from(String password) {
        validatePasswordPattern(password);
        return new Password(password);
    }

    private Password(String password) {
        this.password = password;
    }

    private static void validatePasswordPattern(String password) {
        if(isNotValid(password)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_PASSWORD);
        }
    }

    private static boolean isNotValid(String password) {
        return !PATTERN.matcher(password).matches();
    }

    public String value() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return value().equals(password.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
