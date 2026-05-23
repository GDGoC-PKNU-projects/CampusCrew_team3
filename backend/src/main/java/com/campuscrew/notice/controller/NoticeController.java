package com.campuscrew.notice.controller;

import com.campuscrew.common.dto.ApiResponse;
import com.campuscrew.notice.dto.NoticeRequestDto;
import com.campuscrew.notice.dto.NoticeResponseDto;
import com.campuscrew.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams/{teamId}/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<ApiResponse<NoticeResponseDto>> createNotice(
            @PathVariable Long teamId,
            @RequestBody NoticeRequestDto requestDto) {
        // 임시로 authorId를 1L로 고정 (추후 시큐리티/인증 연동 시 수정)
        Long currentUserId = 1L;
        NoticeResponseDto responseDto = noticeService.createNotice(teamId, currentUserId, requestDto);
        return ResponseEntity.ok(ApiResponse.success(responseDto, "공지가 생성되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NoticeResponseDto>>> getNoticesByTeam(@PathVariable Long teamId) {
        List<NoticeResponseDto> responseDtos = noticeService.getNoticesByTeamId(teamId);
        return ResponseEntity.ok(ApiResponse.success(responseDtos));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponseDto>> getNotice(
            @PathVariable Long teamId, 
            @PathVariable Long noticeId) {
        NoticeResponseDto responseDto = noticeService.getNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponseDto>> updateNotice(
            @PathVariable Long teamId,
            @PathVariable Long noticeId, 
            @RequestBody NoticeRequestDto requestDto) {
        NoticeResponseDto responseDto = noticeService.updateNotice(noticeId, requestDto);
        return ResponseEntity.ok(ApiResponse.success(responseDto, "공지가 수정되었습니다."));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(
            @PathVariable Long teamId,
            @PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok(ApiResponse.success(null, "공지가 삭제되었습니다."));
    }
}
