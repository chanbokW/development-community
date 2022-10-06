package me.snsservice.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * ErrorCode, HttpStatus, ErrorMessage
     * ex) E404,HttpStatus.NOT_FOUND, 존재하지 않은 회원
     */
    NOT_FOUND_MEMBER("E404", HttpStatus.NOT_FOUND, "존재하지 않는 회원");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
