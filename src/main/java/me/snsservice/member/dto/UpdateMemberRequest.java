package me.snsservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.snsservice.member.domain.Member;

@Getter
@AllArgsConstructor
public class UpdateMemberRequest {
    private String nickname;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .password(password)
                .build();
    }
}
