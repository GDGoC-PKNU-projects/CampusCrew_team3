package com.campuscrew.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청 파라미터입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "C002", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 내부 오류가 발생했습니다."),

    // 인증 에러
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}