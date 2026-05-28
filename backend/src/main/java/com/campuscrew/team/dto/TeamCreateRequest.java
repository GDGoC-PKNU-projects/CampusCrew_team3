package com.campuscrew.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TeamCreateRequest {

    @NotBlank(message = "VALID_001")
    @Size(min = 2, max = 30, message = "VALID_TEAM_001")
    private String name;

    @NotBlank(message = "VALID_001")
    @Size(min = 2, max = 30, message = "VALID_TEAM_002")
    private String courseName;

    @Size(max = 500, message = "VALID_TEAM_003")
    private String description;
}