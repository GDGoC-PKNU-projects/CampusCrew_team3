package com.campuscrew.team.repository;

import com.campuscrew.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findAllByUserId(Long userId);
    boolean existsByTeamIdAndUserId(Long teamId, Long userId);
    int countByTeamId(Long teamId);
}