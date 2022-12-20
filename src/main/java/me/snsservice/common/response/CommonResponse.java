package me.snsservice.common.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T> {

    private HttpStatus status;
    private String message;
    private T data;

    @Builder
    private CommonResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> res(HttpStatus status) {
        return res(status, status.name(), null);
    }

    public static <T> CommonResponse<T> res(HttpStatus status, String message) {
        return res(status, message, null);
    }

    public static <T> CommonResponse<T> res(HttpStatus status, T data) {
        return res(status, status.name(), data);
    }

    public static <T> CommonResponse<T> res(HttpStatus status, String message, T data) {
        return CommonResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }


}
