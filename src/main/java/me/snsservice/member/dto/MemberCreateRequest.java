package me.snsservice.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    private String email;
    private String password;
    private String nickname;
}
