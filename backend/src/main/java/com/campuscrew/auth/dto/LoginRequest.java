package com.campuscrew.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Email(message = "VALID_EMAIL_001")
    @NotBlank(message = "VALID_001")
    private String email;

    @NotBlank(message = "VALID_001")
    private String password;
}