package me.snsservice.common.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponse<T> {

    private ApiStatus status;
    private String message;
    private T data;

    @Builder
    private CommonResponse(ApiStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * success 1. 데이터, 메세지 2. 데이터, 3. 데이터와 메세지가 없을 경우
     * @param data 데이터
     * @param message 응답 메세지
     * @param <T> 응답 Dto 타입
     * @return CommonResponse
     */
    // 1
    public static <T> CommonResponse success(T data, String message) {
        return CommonResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }
    // 2
    public static <T> CommonResponse success(T data) {
        return CommonResponse.builder()
                .status(ApiStatus.SUCCESS)
                .data(data)
                .build();
    }
    // 3
    public static <T> CommonResponse success() {
        return CommonResponse.builder()
                .status(ApiStatus.SUCCESS)
                .build();
    }

    /**
     * 실패 응답은 고민 해봐야겠다. ErrorResponse
     */
    public static <T> CommonResponse fail(String message) {
        return CommonResponse.builder()
                .status(ApiStatus.FAIL)
                .message(message)
                .build();
    }

    public static <T> CommonResponse fail(T data) {
        return CommonResponse.builder()
                .status(ApiStatus.FAIL)
                .data(data)
                .build();
    }

    public static <T> CommonResponse fail() {
        return CommonResponse.builder()
                .status(ApiStatus.FAIL)
                .build();
    }

    public static <T> CommonResponse fail(T data, String message) {
        return CommonResponse.builder()
                .status(ApiStatus.FAIL)
                .data(data)
                .message(message)
                .build();
    }

    private enum ApiStatus {
        SUCCESS, FAIL
    }
}
