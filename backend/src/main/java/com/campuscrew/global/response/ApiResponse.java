package com.campuscrew.global.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    // success(data, no msg)
    public static <T> ApiResponse<T> success(T data) {

        return new ApiResponse<>(true, data, null);
    }

    // success(data, msg)
    public static <T> ApiResponse<T> success(T data, String message) {

        return new ApiResponse<>(true, data, message);
    }

    // fail
    public static <T> ApiResponse<T> error(String message) {

        return new ApiResponse<>(false, null, message);
    }
}