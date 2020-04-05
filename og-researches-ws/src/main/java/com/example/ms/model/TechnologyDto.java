package com.example.ms.model;

import lombok.Data;

@Data
public class TechnologyDto {

    private Long id;

    private String userId;

    private String name;

    private int level;

    private String buildState;

    private String howLongToBuild;

    private String buildTime;

    private int metal;

    private int crystal;

    private int deuterium;
}
