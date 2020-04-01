package com.example.ms.model;

import lombok.Data;

@Data
public class BuildingResponse {

    private String name;

    private int level;

    private int metal;

    private int crystal;

    private int deuterium;

    private String buildTime;

    private String howLongToBuild;

    private String buildState;
}
