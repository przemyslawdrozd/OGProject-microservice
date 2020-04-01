package com.example.ms.calculate;

import com.example.ms.exception.buildings.BuildingException;
import com.example.ms.exception.buildings.BuildingNotFoundException;
import com.example.ms.model.BuildingDto;
import com.example.ms.model.BuildingEntity;
import com.example.ms.model.BuildingResponse;
import com.example.ms.model.resources.ResourcesResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class FacilitiesConfiguration implements IFacilitiesConfiguration {

    private final ModelMapper mp;
    private final FacilitiesResources facilitiesResources;
    private final FacilitiesBuildTime facilitiesBuildTime;

    public FacilitiesConfiguration(ModelMapper mp,
                                   FacilitiesResources facilitiesResources,
                                   FacilitiesBuildTime facilitiesBuildTime) {
        this.mp = mp;
        this.facilitiesResources = facilitiesResources;
        this.facilitiesBuildTime = facilitiesBuildTime;
    }

    @Override
    public BuildingResponse configBuildingResponse(BuildingEntity building) {

        return Optional.of(building)
                .map(b ->  mp.map(b, BuildingDto.class))
                .map(this::getSettleBuildingDto)
                .map(b -> mp.map(b, BuildingResponse.class))
                .orElseThrow(() -> new BuildingNotFoundException("building is not found!"));
    }

    @Override
    public BuildingDto configBuilding(BuildingEntity building) {

        return Optional.of(building)
                .map(b ->  mp.map(b, BuildingDto.class))
                .map(this::getSettleBuildingDto)
                .orElseThrow(() -> new BuildingNotFoundException("building is not found!"));
    }

    @Override
    public BuildingEntity getBuildingEntity(BuildingDto buildingDto) {

        return Optional.of(buildingDto)
                .map(b -> mp.map(b, BuildingEntity.class))
                .orElseThrow(() -> new BuildingNotFoundException("building is not found!"));
    }

    @Override
    public ResourcesResponse retrieveResources(BuildingDto building, ResourcesResponse resources) {

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

    public BuildingDto getSettleBuildingDto(BuildingDto building) {

        return Optional.of(building)
                .map(facilitiesBuildTime::setBuildTime)
                .map(facilitiesResources::setBuildingResources)
                .orElseThrow(() -> new BuildingNotFoundException("building is not found!"));
    }
}
