package me.snsservice.common.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.common.error.exception.BusinessException;
import me.snsservice.common.error.response.FieldErrors;
import org.springframework.validation.BindingResult;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private int code;
    private String message;
    private FieldErrors fieldErrors;

    @Builder
    private ErrorResponse(int code, String message, FieldErrors fieldErrors) {
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .fieldErrors(FieldErrors.of(bindingResult))
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, FieldErrors fieldErrors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .fieldErrors(fieldErrors)
                .build();

    }

    public static ErrorResponse of(BusinessException e) {
        return ErrorResponse.of(e.getErrorCode());
    }
}
