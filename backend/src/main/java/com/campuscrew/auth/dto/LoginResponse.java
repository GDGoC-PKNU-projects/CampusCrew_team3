package com.campuscrew.auth.dto;

import com.campuscrew.auth.entity.User;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;
    private final UserInfo user;

    public LoginResponse(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = new UserInfo(user.getId(), user.getName(), user.getEmail());
    }

    @Getter
    public static class UserInfo {
        private final Long id;
        private final String name;
        private final String email;

        public UserInfo(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
}
