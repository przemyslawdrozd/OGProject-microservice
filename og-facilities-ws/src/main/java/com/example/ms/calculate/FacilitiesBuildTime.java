package com.example.ms.calculate;

import com.example.ms.exception.buildings.BuildingNotFoundException;
import com.example.ms.model.BuildingDto;
import org.springframework.stereotype.Component;

@Component
public class FacilitiesBuildTime {

    public BuildingDto setBuildTime(BuildingDto building) {
        return building.getLevel() == 0 ? defaultBuildTime(building) : calculateBuildTime(building);
    }

    private BuildingDto calculateBuildTime(BuildingDto building) {

        // TODO coming soon
            // calculate build time + 30 % time
            building.getBuildTime(); // null
            building.getLevel();

            // set new buildTime
    //        building.setBuildTime();
        return null;
    }

    private BuildingDto defaultBuildTime(BuildingDto building) {

        switch (building.getName()) {

            case "metal":
                building.setBuildTime("00:01:00");
                return building;

            case "crystal":
                building.setBuildTime("00:01:25");
                return building;

            case "deuterium":
                building.setBuildTime("00:01:50");
                return building;

            case "fusion":
                building.setBuildTime("00:05:00");
                return building;

            case "solar":
                building.setBuildTime("00:01:45");
                return building;

            case "metal_storage":
                building.setBuildTime("00:01:10");
                return building;

            case "crystal_storage":
                building.setBuildTime("00:01:20");
                return building;

            case "deuterium_storage":
                building.setBuildTime("00:01:40");
                return building;

            case "robotic":
                building.setBuildTime("00:03:00");
                return building;

            case "shipyard":
                building.setBuildTime("00:03:10");
                return building;

            case "research":
                building.setBuildTime("00:03:20");
                return building;

            case "nanite":
                building.setBuildTime("12:00:00");
                return building;

            case "terraform":
                building.setBuildTime("1:00:00");
                return building;

            default:
                throw new BuildingNotFoundException(building.getName() + " unknown!");
        }
    }
}
