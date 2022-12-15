package me.snsservice.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenRequest {

    private String refreshToken;

    public AccessTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
