package com.campuscrew.notice.dto;

import com.campuscrew.notice.entity.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeResponseDto {
    private Long id;
    private Long teamId;
    private Long authorId;
    private String authorName; // 추가됨
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.teamId = notice.getTeamId();
        this.authorId = notice.getAuthorId();
        // 아직 User 엔티티가 없으므로 임시 값 할당
        this.authorName = "임시 사용자"; 
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
