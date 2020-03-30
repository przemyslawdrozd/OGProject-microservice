package com.example.ms.calculate;

import com.example.ms.exception.buildings.BuildingException;
import com.example.ms.model.BuildingEntity;
import com.example.ms.model.BuildingResponse;
import com.example.ms.model.resources.ResourcesResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FacilitiesBuildProcess {

    public static ResourcesResponse retrieveResources(BuildingEntity buildingEntity, ResourcesResponse resources) {

        BuildingResponse building = FacilitiesResources.setBuildingResources(buildingEntity);

        if (building.getMetal() <= resources.getMetal() &&
            building.getCrystal() <= resources.getCrystal() &&
            building.getDeuterium() <= resources.getDeuterium()) {

            resources.setMetal(resources.getMetal() - building.getMetal());
            resources.setCrystal(resources.getCrystal() - building.getCrystal());
            resources.setDeuterium(resources.getDeuterium() - building.getDeuterium());

            return resources;
        }
        throw new BuildingException("Not enough resources!");
    }

}
