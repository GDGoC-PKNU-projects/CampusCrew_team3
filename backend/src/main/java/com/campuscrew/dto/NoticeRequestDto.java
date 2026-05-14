package com.campuscrew.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDto {

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String content;

    private String author;
}