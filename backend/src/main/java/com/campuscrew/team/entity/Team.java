package com.campuscrew.team.entity;

import com.campuscrew.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teams")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String courseName;

    private String description;

    @Column(nullable = false, unique = true)
    private String joinCode;

    @Column(nullable = false)
    private Long ownerId;
}