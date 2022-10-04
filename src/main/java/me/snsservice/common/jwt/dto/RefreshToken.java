package me.snsservice.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshToken {

    private String refreshToken;
}
