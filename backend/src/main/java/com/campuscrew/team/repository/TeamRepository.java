package com.campuscrew.team.repository;

import com.campuscrew.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByJoinCode(String joinCode);
    boolean existsByJoinCode(String joinCode);

    @Query("SELECT t FROM Team t WHERE t.id IN :teamIds")
    List<Team> findAllByIdIn(@Param("teamIds") List<Long> teamIds);
}