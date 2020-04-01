package com.example.ms.service;

import com.example.ms.calculate.FacilitiesConfiguration;
import com.example.ms.calculate.IFacilitiesConfiguration;
import com.example.ms.exception.buildings.BuildingException;
import com.example.ms.exception.buildings.BuildingNotFoundException;
import com.example.ms.factory.BuildState;
import com.example.ms.factory.FacilitiesFactory;
import com.example.ms.feignClient.ResourcesServiceClient;
import com.example.ms.model.BuildingDto;
import com.example.ms.model.BuildingResponse;
import com.example.ms.model.resources.ResourcesResponse;
import com.example.ms.repository.FacilitiesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class FacilitiesServiceImpl implements FacilitiesService {

    @Value("${service.key.create-resources}")
    private String createValidationKey;

    private final FacilitiesRepository facilitiesRepository;
    private final ResourcesServiceClient resourcesServiceClient;
    private final IFacilitiesConfiguration configuration;

    @Autowired
    public FacilitiesServiceImpl(FacilitiesRepository facilitiesRepository,
                                 ResourcesServiceClient resourcesServiceClient,
                                 IFacilitiesConfiguration configuration) {
        this.facilitiesRepository = facilitiesRepository;
        this.resourcesServiceClient = resourcesServiceClient;
        this.configuration = configuration;
    }

    @Override
    public void createFacilities(String userId) {
        facilitiesRepository.saveAll(FacilitiesFactory.getFacilities(userId));
        log.info("Facilities for user: {} saved", userId);
    }

    @Override
    public List<BuildingResponse> getBuildings(String userId) {

        return facilitiesRepository.findAllByUserId(userId)
                .stream()
                .map(configuration::configBuildingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String levelUp(String userId, String name) {

        BuildingDto buildingDto = facilitiesRepository.findByUserIdAndName(userId, name)
                .map(configuration::configBuilding)
                .orElseThrow(() -> new BuildingNotFoundException("building " + name + " is not found!"));

        if (!BuildState.READY.equals(buildingDto.getBuildState())) {
            log.error("Invalid resources or build state");
            throw new BuildingException("Building " + name + " is not ready: " + buildingDto.getBuildState());
        }

        ResourcesResponse resources = resourcesServiceClient.getResourcesByUserId(userId).getBody();

        resourcesServiceClient.updateResources(userId, createValidationKey,
                configuration.retrieveResources(buildingDto, resources));

        buildingDto.setBuildState("PROGRESS");
        buildingDto.setHowLongToBuild(buildingDto.getBuildTime());

        facilitiesRepository.updateBuildStateAsFreezeByUserId(userId);
        facilitiesRepository.save(configuration.getBuildingEntity(buildingDto));
        return name + " started to build!";
    }
}
