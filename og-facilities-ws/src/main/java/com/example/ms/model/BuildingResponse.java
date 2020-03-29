package com.example.ms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BuildingResponse {

    private String name;

    private int level;

    private int metal;

    private int crystal;

    private int deuterium;

    private String buildTime;

    private Date howLongToBuild;

    private String buildState;
}
