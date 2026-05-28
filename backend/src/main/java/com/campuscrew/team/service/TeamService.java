package com.campuscrew.team.service;

import com.campuscrew.global.exception.CustomException;
import com.campuscrew.global.exception.ErrorCode;
import com.campuscrew.team.dto.*;
import com.campuscrew.team.entity.Team;
import com.campuscrew.team.entity.TeamMember;
import com.campuscrew.team.repository.TeamMemberRepository;
import com.campuscrew.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public TeamCreateResponse createTeam(Long userId, TeamCreateRequest request) {
        String joinCode = generateJoinCode();

        Team team = Team.builder()
                .name(request.getName())
                .courseName(request.getCourseName())
                .description(request.getDescription())
                .joinCode(joinCode)
                .ownerId(userId)
                .build();

        Team savedTeam = teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
                .teamId(savedTeam.getId())
                .userId(userId)
                .role("OWNER")
                .build();

        teamMemberRepository.save(teamMember);

        return new TeamCreateResponse(savedTeam);
    }

    public List<TeamListResponse> getMyTeams(Long userId) {
        List<TeamMember> memberships = teamMemberRepository.findAllByUserId((userId));

        return memberships.stream()
                .map(membership -> {
                    Team team = teamRepository.findById(membership.getTeamId())
                            .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
                    int memberCount = teamMemberRepository.countByTeamId(team.getId());
                    return new TeamListResponse(team, memberCount);
                })
                .collect(Collectors.toList());
    }

    public TeamDetailResponse getTeamDetail(Long userId, Long teamId) {
        validateMembership(userId, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        int memberCount = teamMemberRepository.countByTeamId(teamId);
        return new TeamDetailResponse(team, memberCount);
    }

    @Transactional
    public TeamJoinResponse joinTeam(Long userId, TeamJoinRequest request) {
        Team team = teamRepository.findByJoinCode(request.getJoinCode())
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        if(teamMemberRepository.existsByTeamIdAndUserId(team.getId(), userId)) {
            throw new CustomException(ErrorCode.ALREADY_TEAM_MEMBER);
        }

        TeamMember teamMember = TeamMember.builder()
                .teamId(team.getId())
                .userId(userId)
                .role("MEMBER")
                .build();

        teamMemberRepository.save(teamMember);

        return new TeamJoinResponse(team);
    }

    public void validateMembership(Long userId, Long teamId) {
        if(!teamMemberRepository.existsByTeamIdAndUserId(teamId, userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN_TEAM);
        }
    }

    private String generateJoinCode() {
        while (true) {
            String code = UUID.randomUUID().toString()
                    .replace("-", "")
                    .substring(0, 6)
                    .toUpperCase();
            if (!teamRepository.existsByJoinCode(code)) {
                return code;
            }
        }
    }
}
