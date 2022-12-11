package me.snsservice.common.exception;

import lombok.Getter;
import me.snsservice.common.exception.ErrorCode;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
