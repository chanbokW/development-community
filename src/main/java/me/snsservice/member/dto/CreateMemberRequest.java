package me.snsservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {

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
                .role(Role.ROLE_MEMBER)
                .build();
    }
}
