package com.example.ms.calculate;

import com.example.ms.model.BuildingDto;
import com.example.ms.model.BuildingEntity;
import com.example.ms.model.BuildingResponse;
import com.example.ms.model.resources.ResourcesResponse;

public interface IFacilitiesConfiguration {

    BuildingResponse configBuildingResponse(BuildingEntity entity);

    BuildingDto configBuilding(BuildingEntity building);

    BuildingEntity getBuildingEntity(BuildingDto buildingDto);

    ResourcesResponse retrieveResources(BuildingDto building, ResourcesResponse resources);
}
