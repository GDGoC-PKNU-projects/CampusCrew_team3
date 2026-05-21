package com.campuscrew.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    // 인증
    SIGNUP_SUCCESS(HttpStatus.CREATED, "SUCCESS_AUTH_001", "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "SUCCESS_AUTH_002", "로그인에 성공했습니다."),
    ME_SUCCESS(HttpStatus.OK, "SUCCESS_AUTH_003", null),

    // 팀
    TEAM_LIST_SUCCESS(HttpStatus.OK, "SUCCESS_TEAM_001", null),
    TEAM_CREATE_SUCCESS(HttpStatus.CREATED, "SUCCESS_TEAM_002", "팀이 생성되었습니다."),
    TEAM_DETAIL_SUCCESS(HttpStatus.OK, "SUCCESS_TEAM_003", null),
    TEAM_JOIN_SUCCESS(HttpStatus.OK, "SUCCESS_TEAM_004", "팀에 참여했습니다."),

    // 대시보드
    DASH_BOARD_SUCCESS(HttpStatus.OK, "SUCCESS_DASH_001", null),

    // 공지
    NOTICE_LIST_SUCCESS(HttpStatus.OK, "SUCCESS_NOTICE_001", null),
    NOTICE_CREATE_SUCCESS(HttpStatus.CREATED, "SUCCESS_NOTICE_002", "공지가 생성되었습니다."),
    NOTICE_DETAIL_SUCCESS(HttpStatus.OK, "SUCCESS_NOTICE_003", null),
    NOTICE_UPDATE_SUCCESS(HttpStatus.OK, "SUCCESS_NOTICE_004", "공지가 수정되었습니다."),
    NOTICE_DELETE_SUCCESS(HttpStatus.OK, "SUCCESS_NOTICE_005", "공지가 삭제되었습니다."),

    // 일정
    SCHEDULE_LIST_SUCCESS(HttpStatus.OK, "SUCCESS_SCHED_001", null),
    SCHEDULE_CREATE_SUCCESS(HttpStatus.CREATED, "SUCCESS_SCHED_002", "일정이 생성되었습니다."),
    SCHEDULE_DETAIL_SUCCESS(HttpStatus.OK, "SUCCESS_SCHED_003", null),
    SCHEDULE_UPDATE_SUCCESS(HttpStatus.OK, "SUCCESS_SCHED_004", "일정이 수정되었습니다."),
    SCHEDULE_DELETE_SUCCESS(HttpStatus.OK, "SUCCESS_SCHED_005", "일정이 삭제되었습니다."),

    // 할 일
    TASK_LIST_SUCCESS(HttpStatus.OK, "SUCCESS_TASK_001", null),
    TASK_CREATE_SUCCESS(HttpStatus.CREATED, "SUCCESS_TASK_002", "할 일이 생성되었습니다."),
    TASK_DETAIL_SUCCESS(HttpStatus.OK, "SUCCESS_TASK_003", null),
    TASK_UPDATE_SUCCESS(HttpStatus.OK, "SUCCESS_TASK_004", "할 일이 수정되었습니다."),
    TASK_STATUS_SUCCESS(HttpStatus.OK, "SUCCESS_TASK_005", "할 일 상태가 변경되었습니다."),
    TASK_DELETE_SUCCESS(HttpStatus.OK, "SUCCESS_TASK_006", "할 일이 삭제되었습니다."),

    // 회의록
    NOTE_LIST_SUCCESS(HttpStatus.OK, "SUCCESS_NOTE_001", null),
    NOTE_CREATE_SUCCESS(HttpStatus.CREATED, "SUCCESS_NOTE_002", "회의록이 생성되었습니다."),
    NOTE_DETAIL_SUCCESS(HttpStatus.OK, "SUCCESS_NOTE_003", null),
    NOTE_UPDATE_SUCCESS(HttpStatus.OK, "SUCCESS_NOTE_004", "회의록이 수정되었습니다."),
    NOTE_DELETE_SUCCESS(HttpStatus.OK, "SUCCESS_NOTE_005", "회의록이 삭제되었습니다."),

    // AI
    AI_ACTION_ITEMS_SUCCESS(HttpStatus.OK, "SUCCESS_AI_001", null),
    AI_TONE_SUCCESS(HttpStatus.OK, "SUCCESS_AI_002", null),
    AI_SUMMARY_SUCCESS(HttpStatus.OK, "SUCCESS_AI_003", null),
    AI_QNA_SUCCESS(HttpStatus.OK, "SUCCESS_AI_004", null);

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
