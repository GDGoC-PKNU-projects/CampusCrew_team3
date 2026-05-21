package com.campuscrew.global.response;

import com.campuscrew.global.exception.ErrorCode;
import com.campuscrew.global.exception.SuccessCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private String code;
    private T data;
    private String message;

    private ApiResponse(boolean success, String code, T data,  String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    // success
    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(true, successCode.getCode(), data, successCode.getMessage());
    }

    // fail
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getCode(), null, errorCode.getMessage());
    }

}