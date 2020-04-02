package com.example.ms.calculate;

import com.example.ms.exeptions.FacilitiesNotFoundException;
import com.example.ms.model.ResourcesEntity;
import com.example.ms.repository.ResourcesRepository;
import com.example.ms.shared.FacilitiesServiceClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Log4j2
@Component
@EnableScheduling
public class ResourcesScheduler {

    @Value("${service.key.create-resources}")
    private String createValidationKey;

    @Value("${rate.resourceIncreaseRate}")
    private int resourceIncreaseRate;

    private final ResourcesRepository resourcesRepository;
    private final FacilitiesServiceClient facilitiesServiceClient;

    public ResourcesScheduler(ResourcesRepository resourcesRepository,
                              FacilitiesServiceClient facilitiesServiceClient) {
        this.resourcesRepository = resourcesRepository;
        this.facilitiesServiceClient = facilitiesServiceClient;
    }

    @Scheduled(fixedDelay = 1000)
    public void increaseResources() {
        resourcesRepository.findAll().forEach(this::increaseResources);
    }

    private void increaseResources(ResourcesEntity resourcesEntity) {

        try {
            Objects.requireNonNull(facilitiesServiceClient.getFacilitiesLevels(resourcesEntity.getUserId(), createValidationKey)
                    .getBody()).forEach(building -> {

                if (building.getName().equals("metal")) {
                    resourcesEntity.setMetal(resourcesEntity.getMetal() +
                            (building.getLevel() * resourceIncreaseRate) + 3);
                }

                if (building.getName().equals("crystal")) {
                    resourcesEntity.setCrystal(resourcesEntity.getCrystal() +
                            (building.getLevel() * resourceIncreaseRate) + 2);
                }

                if (building.getName().equals("deuterium")) {
                    resourcesEntity.setDeuterium(resourcesEntity.getDeuterium() +
                            (building.getLevel() * resourceIncreaseRate) + 1);
                }

                resourcesRepository.save(resourcesEntity);
            });
        } catch (RuntimeException e) {
            log.info("Client service down or wrong userId : \n" + e.getMessage());
            throw new FacilitiesNotFoundException("Facilities not found - wrong userId or client server down");
        }


    }
}
