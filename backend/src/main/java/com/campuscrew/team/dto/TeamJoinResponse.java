package com.campuscrew.team.dto;

import com.campuscrew.team.entity.Team;
import lombok.Getter;

@Getter
public class TeamJoinResponse {
    private final Long teamId;
    private final String teamName;

    public TeamJoinResponse(Team team) {
        this.teamId = team.getId();
        this.teamName = team.getName();
    }
}
