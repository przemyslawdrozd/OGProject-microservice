package com.example.ms.calculate;

import com.example.ms.exception.buildings.BuildingNotFoundException;
import com.example.ms.model.BuildingEntity;
import com.example.ms.model.BuildingResponse;
import org.modelmapper.ModelMapper;

public class FacilitiesResources {

    public static BuildingResponse setBuildingResources(BuildingEntity buildingEntity) {

        BuildingResponse building = new ModelMapper().map(buildingEntity, BuildingResponse.class);

        return buildingEntity.getLevel() == 0 ? defaultResources(building) : calculateResources(building);
    }

    private static BuildingResponse calculateResources(BuildingResponse building) {

        int lvl = building.getLevel();
        double rate = 1.25;

        int metalNextLevel = (int) (building.getMetal() * lvl * rate);
        int crystalNextLevel = (int) (building.getMetal() * lvl * rate);
        int deuteriumNextLevel = (int) (building.getMetal() * lvl * rate);

        building.setMetal(metalNextLevel);
        building.setCrystal(crystalNextLevel);
        building.setDeuterium(deuteriumNextLevel);

        return building;
    }

    private static BuildingResponse defaultResources(BuildingResponse building) {

        switch (building.getName()) {

            case "metal":
                building.setMetal(100);
                building.setCrystal(30);
                building.setDeuterium(0);
                building.setBuildTime("00:01:00");
                return building;

            case "crystal":
                building.setMetal(120);
                building.setCrystal(60);
                building.setDeuterium(0);
                building.setBuildTime("00:01:20");
                return building;

            case "deuterium":
                building.setMetal(130);
                building.setCrystal(70);
                building.setDeuterium(0);
                building.setBuildTime("00:01:50");
                return building;

            case "fusion":
                building.setMetal(500);
                building.setCrystal(200);
                building.setDeuterium(100);
                building.setBuildTime("00:05:00");
                return building;

            case "solar":
                building.setMetal(100);
                building.setCrystal(35);
                building.setDeuterium(0);
                building.setBuildTime("00:01:40");
                return building;

            case "metal_storage":
                building.setMetal(100);
                building.setCrystal(0);
                building.setDeuterium(0);
                building.setBuildTime("00:01:00");
                return building;

            case "crystal_storage":
                building.setMetal(100);
                building.setCrystal(50);
                building.setDeuterium(0);
                building.setBuildTime("00:01:20");
                return building;

            case "deuterium_storage":
                building.setMetal(100);
                building.setCrystal(100);
                building.setDeuterium(0);
                building.setBuildTime("00:01:40");
                return building;

            case "robotic":
                building.setMetal(200);
                building.setCrystal(60);
                building.setDeuterium(80);
                building.setBuildTime("00:03:00");
                return building;

            case "shipyard":
                building.setMetal(200);
                building.setCrystal(80);
                building.setDeuterium(60);
                building.setBuildTime("00:03:00");
                return building;

            case "research":
                building.setMetal(80);
                building.setCrystal(200);
                building.setDeuterium(60);
                building.setBuildTime("00:03:00");
                return building;

            case "nanite":
                building.setMetal(1_000_000);
                building.setCrystal(500_000);
                building.setDeuterium(100_000);
                building.setBuildTime("12:00:00");
                return building;

            case "terraform":
                building.setMetal(50_000);
                building.setCrystal(100_000);
                building.setDeuterium(100_000);
                building.setBuildTime("1:00:00");
                return building;

            default:
                throw new BuildingNotFoundException(building.getName() + " unknown!");
        }
    }
}
