package me.snsservice.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtExpiration {
    ACCESS_TOKEN_EXPIRED_TIME("accessToken만료시간", 1000L * 60* 60 * 1),
    REFRESH_TOKEN_EXPIRED_TIME("refreshToken 만료시간", 1000L * 60 * 60 * 24 * 7);

    private final String description;
    private final Long time;
}
