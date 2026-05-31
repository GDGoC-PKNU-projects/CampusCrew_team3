package com.campuscrew.notice.service;

import com.campuscrew.notice.dto.NoticeRequestDto;
import com.campuscrew.notice.dto.NoticeResponseDto;
import com.campuscrew.notice.entity.Notice;
import com.campuscrew.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public NoticeResponseDto createNotice(Long teamId, Long authorId, NoticeRequestDto requestDto) {
        Notice notice = Notice.builder()
                .teamId(teamId)
                .authorId(authorId)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        Notice savedNotice = noticeRepository.save(notice);
        return new NoticeResponseDto(savedNotice);
    }

    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getNoticesByTeamId(Long teamId) {
        return noticeRepository.findAllByTeamIdOrderByCreatedAtDesc(teamId)
                .stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다. id=" + id));
        return new NoticeResponseDto(notice);
    }

    @Transactional
    public NoticeResponseDto updateNotice(Long id, NoticeRequestDto requestDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다. id=" + id));
        
        notice.update(requestDto.getTitle(), requestDto.getContent());
        return new NoticeResponseDto(notice);
    }

    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다. id=" + id));
        noticeRepository.delete(notice);
    }
}
