package me.snsservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * ErrorCode, HttpStatus, ErrorMessage
     * ex) 404,HttpStatus.NOT_FOUND, 존재하지 않은 회원
     */

    //common
    METHOD_NOT_ALLOWED(405, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 Method 입력값"),
    INVALID_INPUT_VALUE(400, HttpStatus.BAD_REQUEST, "유효하지 않은 입력값"),
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "bad request"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "internal server error"),

    // auth
    INVALID_TOKEN_VALUE(401, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰"),
    NOT_FOUND_TOKEN(401, HttpStatus.UNAUTHORIZED, "로그인이 필요"),

    // member
    NOT_FOUND_MEMBER(404, HttpStatus.NOT_FOUND, "존재하지 않는 회원"),
    EXISTS_EMAIL(403, HttpStatus.FORBIDDEN, "이미 존재하는 이메일"),
    EXISTS_NICKNAME(403, HttpStatus.FORBIDDEN, "이미 존재하는 닉네임"),
    INVALID_MEMBER_NICKNAME(400, HttpStatus.BAD_REQUEST, "잘못된 닉네임 형식"),
    INVALID_MEMBER_PASSWORD(400, HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식"),
    INVALID_MEMBER_EMAIL(400, HttpStatus.BAD_REQUEST, "잘못된 이메일 형식"),
    NOT_INPUT_MEMBER_PASSWORD(400, HttpStatus.BAD_REQUEST, "입력하지 않은 패스워드"),
    NOT_INPUT_MEMBER_EMAIL(400, HttpStatus.BAD_REQUEST, "입력하지 않은 이메일"),
    NOT_INPUT_MEMBER_NICKNAME(400, HttpStatus.BAD_REQUEST, "입력하지 않은 닉네임"),


    // article
    NOT_FOUND_ARTICLE(404, HttpStatus.NOT_FOUND, "존재하지 않은 게시물"),
    UNAUTHORIZED_ARTICLE_MEMBER(401, HttpStatus.UNAUTHORIZED, ""),
    INVALID_ARTICLE_TITLE(400, HttpStatus.BAD_REQUEST, "제목은 2자이상 50자이하 문자를 입력하세요."),
    INVALID_ARTICLE_CONTENT(400, HttpStatus.BAD_REQUEST, "본문은 2자이상 500자이하 문자를 입력하세요."),

    // tag
    DUPLICATE_TAG(400,HttpStatus.BAD_REQUEST, "중복되는 태그 입력");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
    }
