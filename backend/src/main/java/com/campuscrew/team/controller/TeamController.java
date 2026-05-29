package com.campuscrew.team.controller;

import com.campuscrew.auth.entity.User;
import com.campuscrew.global.exception.SuccessCode;
import com.campuscrew.global.response.ApiResponse;
import com.campuscrew.team.dto.TeamCreateRequest;
import com.campuscrew.team.dto.TeamCreateResponse;
import com.campuscrew.team.dto.TeamJoinRequest;
import com.campuscrew.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTeam(@RequestBody @Valid TeamCreateRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TeamCreateResponse response = teamService.createTeam(user.getId(), request);
        return ResponseEntity
                .status(SuccessCode.TEAM_CREATE_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.TEAM_CREATE_SUCCESS, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getMyTeams() {
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TEAM_LIST_SUCCESS, teamService.getMyTeams(user.getId())));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<ApiResponse<?>> getTeamDetail(@PathVariable Long teamId) {
        User user  = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TEAM_DETAIL_SUCCESS, teamService.getTeamDetail(user.getId(), teamId)));
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<?>> joinTeam(@RequestBody @Valid TeamJoinRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TEAM_JOIN_SUCCESS, teamService.joinTeam(user.getId(), request)));
    }
}
