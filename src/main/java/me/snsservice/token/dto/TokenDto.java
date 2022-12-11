package me.snsservice.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    private AccessToken accessToken;
    private RefreshToken refreshToken;
}
