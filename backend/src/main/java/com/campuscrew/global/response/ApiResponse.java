package com.campuscrew.global.response;

import com.campuscrew.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String code;
    private String message;

    private ApiResponse(boolean success, T data, String code, String message) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    // success(data, no msg)
    public static <T> ApiResponse<T> success(T data) {

        return new ApiResponse<>(true, data, null, null);
    }

    // success(data, msg)
    public static <T> ApiResponse<T> success(T data, String message) {

        return new ApiResponse<>(true, data, null, message);
    }

    // fail
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {

        return new ApiResponse<>(false, null, errorCode.getCode(), errorCode.getMessage());
    }
}