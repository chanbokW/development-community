package me.snsservice.auth.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.member.domain.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginMember {
    private Long id;
    private String email;

    public LoginMember(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
