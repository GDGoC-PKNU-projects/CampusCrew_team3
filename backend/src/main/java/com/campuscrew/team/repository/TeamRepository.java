package com.campuscrew.team.repository;

import com.campuscrew.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByJoinCode(String joinCode);
    boolean existsByJoinCode(String joinCode);
}