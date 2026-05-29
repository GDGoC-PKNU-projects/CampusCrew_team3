package com.campuscrew.team.repository;

import com.campuscrew.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findAllByUserId(Long userId);
    boolean existsByTeamIdAndUserId(Long teamId, Long userId);
    int countByTeamId(Long teamId);

    @Query("SELECT tm.teamId, Count(tm) FROM TeamMember tm WHERE tm.teamId IN :teamIds GROUP BY tm.teamId")
    List<Object[]> countByTeamIdIn(@Param("teamIds") List<Long> teamIds);
}