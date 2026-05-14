package com.campuscrew.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}