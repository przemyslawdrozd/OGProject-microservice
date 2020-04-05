package com.example.ms.service;

import com.example.ms.calculate.ResearchesConfiguration;
import com.example.ms.exception.technology.TechNotFoundException;
import com.example.ms.exception.technology.TechnologyException;
import com.example.ms.factory.BuildState;
import com.example.ms.factory.ResearchesFactory;
import com.example.ms.model.TechnologyDto;
import com.example.ms.model.TechnologyResponse;
import com.example.ms.model.resources.ResourcesResponse;
import com.example.ms.repository.ResearchesRepository;
import com.example.ms.shared.ResourcesServiceClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ResearchesServiceImpl implements ResearchesService {

    @Value("${service.key.create-resources}")
    private String createValidationKey;

    private final ResearchesRepository researchesRepository;
    private final ResourcesServiceClient resourcesServiceClient;
    private final ResearchesConfiguration researchesConfiguration;

    @Autowired
    public ResearchesServiceImpl(ResearchesRepository researchesRepository,
                                 ResourcesServiceClient resourcesServiceClient,
                                 ResearchesConfiguration researchesConfiguration) {
        this.researchesRepository = researchesRepository;
        this.resourcesServiceClient = resourcesServiceClient;
        this.researchesConfiguration = researchesConfiguration;
    }

    @Override
    public void createResearches(String userId) {
        researchesRepository.saveAll(ResearchesFactory.getResearches(userId));
        log.info("Researches for user: {} saved", userId);
    }

    @Override
    public List<TechnologyResponse> getResearches(String userId) {

        return researchesRepository.findAllByUserId(userId)
                .stream()
                .map(researchesConfiguration::configTechResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String levelUp(String userId, String name) {
        TechnologyDto techDto = researchesRepository.findByUserIdAndName(userId, name)
                .map(researchesConfiguration::configTech)
                .orElseThrow(() -> new TechNotFoundException("Tech " + name + " is not found!"));

        if (!BuildState.READY.equals(techDto.getBuildState())) {
            log.error("Invalid tech or build state");
            throw new TechnologyException("Tech " + name + " is not ready: " + techDto.getBuildState());
        }

        ResourcesResponse resources = resourcesServiceClient.getResourcesByUserId(userId).getBody();

        resourcesServiceClient.updateResources(userId, createValidationKey,
                researchesConfiguration.retrieveResources(techDto, resources));

        techDto.setBuildState(BuildState.PROGRESS);
        techDto.setHowLongToBuild(techDto.getBuildTime());

        researchesRepository.updateTechStateAsFreezeByUserId(userId);
        researchesRepository.save(researchesConfiguration.getTechnologyEntity(techDto));
        return name + " started to build!";
    }
}
