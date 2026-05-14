package com.campuscrew.auth.dto;

import com.campuscrew.auth.entity.User;
import lombok.Getter;

@Getter
public class MeResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String studentId;
    
    public MeResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentId = user.getStudentId();
    }
}
