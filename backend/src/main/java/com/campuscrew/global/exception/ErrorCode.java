package com.campuscrew.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 인증 (401)
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_001", "로그인 정보가 올바르지 않습니다."),
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "AUTH_002", "인증 토큰이 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_003", "인증 토큰이 만료되었습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH_004", "유효하지 않은 인증 토큰입니다."),

    // 권한 (403)
    FORBIDDEN_TEAM(HttpStatus.FORBIDDEN, "FORBIDDEN_001", "해당 팀에 접근 권한이 없습니다."),
    FORBIDDEN_UPDATE(HttpStatus.FORBIDDEN, "FORBIDDEN_002", "작성자만 수정 할 수 있습니다."),
    FORBIDDEN_DELETE(HttpStatus.FORBIDDEN, "FORBIDDEN_003", "작성자만 삭제할 수 있습니다."),

    // 리소스 없음 (404)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND_001", "존재하지 않는 사용자입니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND_002", "존재하지 않는 팀입니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND_003", "존재하지 않는 공지입니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND_004", "존재하지 않는 일정입니다."),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND_005", "존재하지 않는 할 일입니다."),
    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND_006", "존재하지 않는 회의록입니다."),

    // 공통 검증
    MISSING_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "VALID_001", "필수 입력값이 누락 되었습니다."),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "VALID_002", "입력값 형식이 올바르지 않습니다."),

    // 회원가입 검증
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "VALID_EMAIL_001", "이메일 형식이 올바르지 않습니다."),
    INVALID_EMAIL_LENGTH(HttpStatus.BAD_REQUEST, "VALID_EMAIL_002", "이메일은 최대 100자까지 입력할 수 있습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "VALID_PW_001", "비밀번호는 8자 이상 20자 이하로 입력해야 합니다."),
    INVALID_NAME(HttpStatus.BAD_REQUEST, "VALID_NAME_001", "이름은 2자 이상 20자 이하로 입력해야 합니다."),
    INVALID_STUDENT_ID_FORMAT(HttpStatus.BAD_REQUEST, "VALID_STUID_001", "학번은 숫자만 입력해야 합니다."),
    INVALID_STUDENT_ID_LENGTH(HttpStatus.BAD_REQUEST, "VALID_STUID_002", "학번은 8자 이상 10자 이하로 입력해야 합니다."),

    // 팀 검증

    // 공지 검증

    // 일정 검증

    // 할 일 검증

    // 회의록 검증


    // 충돌 (409)
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "CONFLICT_001", "이미 사용중인 이메일입니다."),
    DUPLICATE_STUDENT_ID(HttpStatus.CONFLICT, "CONFLICT_002", "이미 사용중인 학번입니다."),
    ALREADY_TEAM_MEMBER(HttpStatus.CONFLICT, "CONFLICT_003", "이미 참여중인 팀입니다."),

    // AI 에러


    // Server Error (500)
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버 오류가 발생하였습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
