package com.example.ms.service;

import com.example.ms.calculate.FacilitiesBuildProcess;
import com.example.ms.calculate.FacilitiesResources;
import com.example.ms.exception.buildings.BuildingException;
import com.example.ms.exception.buildings.BuildingNotFoundException;
import com.example.ms.factory.FacilitiesFactory;
import com.example.ms.feignClient.ResourcesServiceClient;
import com.example.ms.model.BuildingEntity;
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

    @Autowired
    public FacilitiesServiceImpl(FacilitiesRepository facilitiesRepository,
                                 ResourcesServiceClient resourcesServiceClient) {
        this.facilitiesRepository = facilitiesRepository;
        this.resourcesServiceClient = resourcesServiceClient;
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
                .map(FacilitiesResources::setBuildingResources)
                .collect(Collectors.toList());
    }

    @Override
    public String levelUp(String userId, String name) {

        BuildingEntity buildingEntity = facilitiesRepository.findByUserIdAndName(userId, name)
                .orElseThrow(() -> new BuildingNotFoundException(userId + " or " + name + " not found!"));

        if (!"READY".equals(buildingEntity.getBuildState())) {
            log.error("Invalid resources or build state");
            throw new BuildingException("Building " + name + " is not ready: " + buildingEntity.getBuildState());
        }

        ResourcesResponse resources = resourcesServiceClient.getResourcesByUserId(userId).getBody();

        resourcesServiceClient.updateResources(userId, createValidationKey,
                FacilitiesBuildProcess.retrieveResources(buildingEntity, resources));

        buildingEntity.setBuildState("PROGRESS");

        facilitiesRepository.updateBuildStateAsFreezeByUserId(userId);
        facilitiesRepository.save(buildingEntity);
        return name + " started to build!";
    }
}
