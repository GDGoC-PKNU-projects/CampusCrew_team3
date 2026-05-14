package com.campuscrew.auth.dto;

import com.campuscrew.auth.entity.User;
import lombok.Getter;

@Getter
public class SignupResponse {
    private final Long id;
    private final String name;
    private final String email;

    public SignupResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
