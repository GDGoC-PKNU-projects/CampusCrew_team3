package com.campuscrew.team.dto;

import com.campuscrew.team.entity.Team;
import lombok.Getter;

@Getter
public class TeamCreateResponse {
    private final Long id;
    private final String name;
    private final String courseName;
    private final String description;
    private final String joinCode;

    public TeamCreateResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.courseName = team.getCourseName();
        this.description = team.getDescription();
        this.joinCode = team.getJoinCode();
    }
}
