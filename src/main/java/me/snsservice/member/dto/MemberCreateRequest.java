package me.snsservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "password는 필수 값입니다.")
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank(message = "닉네임은 필수 값입니다.")
    @Length(min = 2, max = 20)
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(Role.MEMBER)
                .build();
    }
}
