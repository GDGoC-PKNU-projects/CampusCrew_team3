package com.campuscrew.team.dto;

import com.campuscrew.team.entity.Team;
import lombok.Getter;

@Getter
public class TeamDetailResponse {
    private final Long id;
    private final String name;
    private final String courseName;
    private final String description;
    private final String joinCode;
    private final Long ownerId;
    private final int memberCount;

    public TeamDetailResponse(Team team, int memberCount) {
        this.id = team.getId();
        this.name = team.getName();
        this.courseName = team.getCourseName();
        this.description = team.getDescription();
        this.joinCode = team.getJoinCode();
        this.ownerId = team.getOwnerId();
        this.memberCount = memberCount;
    }
}
