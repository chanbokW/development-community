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
public class Nickname {

    private static final Pattern PATTERN =
            Pattern.compile("^[0-9a-zA-Z가-힣]+(?:\\s+[0-9a-zA-Z가-힣]+)*$");
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;

    @Column(name = "nickname")
    private String nickname;

    public Nickname(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    private void validate(String nickname) {
        if (Objects.isNull(nickname)) {
           throw new BusinessException(ErrorCode.NOT_INPUT_MEMBER_NICKNAME);
        }

        if(nickname.length() < MIN_LENGTH || nickname.length() > MAX_LENGTH
            || !PATTERN.matcher(nickname).matches()) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_NICKNAME);
        }
    }

    public String value() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nickname nickname = (Nickname) o;
        return value().equals(nickname.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value());
    }
}
