package com.example.ms.calculate;

import com.example.ms.exception.buildings.BuildingNotFoundException;
import com.example.ms.model.BuildingDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FacilitiesResources {

    @Value("${rate.neededResources}")
    private int neededResourcesRate;

    public BuildingDto setBuildingResources(BuildingDto building) {
        return building.getLevel() == 0 ? defaultResources(building) : calculateResources(building);
    }

    private BuildingDto calculateResources(BuildingDto buildingDto) {

        BuildingDto building = defaultResources(buildingDto);

        int metalNextLevel = building.getMetal()  * neededResourcesRate;
        int crystalNextLevel = building.getCrystal() *  neededResourcesRate;
        int deuteriumNextLevel = building.getDeuterium() * neededResourcesRate;

        building.setMetal(metalNextLevel);
        building.setCrystal(crystalNextLevel);
        building.setDeuterium(deuteriumNextLevel);

        return building;
    }

    private BuildingDto defaultResources(BuildingDto building) {

        switch (building.getName()) {

            case "metal":
                building.setMetal(100);
                building.setCrystal(30);
                building.setDeuterium(0);
                return building;

            case "crystal":
                building.setMetal(120);
                building.setCrystal(60);
                building.setDeuterium(0);
                return building;

            case "deuterium":
                building.setMetal(130);
                building.setCrystal(70);
                building.setDeuterium(0);
                return building;

            case "fusion":
                building.setMetal(500);
                building.setCrystal(200);
                building.setDeuterium(100);
                return building;

            case "solar":
                building.setMetal(100);
                building.setCrystal(35);
                building.setDeuterium(0);
                return building;

            case "metal_storage":
                building.setMetal(100);
                building.setCrystal(0);
                building.setDeuterium(0);
                return building;

            case "crystal_storage":
                building.setMetal(100);
                building.setCrystal(50);
                building.setDeuterium(0);
                return building;

            case "deuterium_storage":
                building.setMetal(100);
                building.setCrystal(100);
                building.setDeuterium(0);
                return building;

            case "robotic":
                building.setMetal(200);
                building.setCrystal(60);
                building.setDeuterium(80);
                return building;

            case "shipyard":
                building.setMetal(200);
                building.setCrystal(80);
                building.setDeuterium(60);
                return building;

            case "research":
                building.setMetal(80);
                building.setCrystal(200);
                building.setDeuterium(60);
                return building;

            case "nanite":
                building.setMetal(1_000_000);
                building.setCrystal(500_000);
                building.setDeuterium(100_000);
                return building;

            case "terraform":
                building.setMetal(50_000);
                building.setCrystal(100_000);
                building.setDeuterium(100_000);
                return building;

            default:
                throw new BuildingNotFoundException(building.getName() + " unknown!");
        }
    }
}
