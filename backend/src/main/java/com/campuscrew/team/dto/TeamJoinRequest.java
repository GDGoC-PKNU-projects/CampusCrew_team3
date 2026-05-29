package com.campuscrew.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class TeamJoinRequest {

    @NotBlank(message = "VALID_001")
    @Pattern(regexp = "^[A-Z0-9]{6}$", message = "VALID_TEAM_004")
    private String joinCode;
}
