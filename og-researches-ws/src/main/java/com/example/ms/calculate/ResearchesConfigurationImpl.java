package com.example.ms.calculate;

import com.example.ms.exception.technology.TechNotFoundException;
import com.example.ms.exception.technology.TechnologyException;
import com.example.ms.model.TechnologyDto;
import com.example.ms.model.TechnologyEntity;
import com.example.ms.model.TechnologyResponse;
import com.example.ms.model.resources.ResourcesResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResearchesConfigurationImpl implements ResearchesConfiguration {

    private final ModelMapper mp;
    private final ResearchesResources researchesResources;
    private final ResearchesBuildTime researchesBuildTime;

    @Autowired
    public ResearchesConfigurationImpl(ModelMapper mp,
                                       ResearchesResources researchesResources,
                                       ResearchesBuildTime researchesBuildTime) {
        this.mp = mp;
        this.researchesResources = researchesResources;
        this.researchesBuildTime = researchesBuildTime;
    }

    @Override
    public TechnologyResponse configTechResponse(TechnologyEntity tech) {

        return Optional.of(tech)
                .map(b ->  mp.map(b, TechnologyDto.class))
                .map(this::getSettleTechDto)
                .map(b -> mp.map(b, TechnologyResponse.class))
                .orElseThrow(() -> new TechNotFoundException("Tech is not found!"));
    }

    @Override
    public TechnologyDto configTech(TechnologyEntity tech) {
        return Optional.of(tech)
                .map(b ->  mp.map(b, TechnologyDto.class))
                .map(this::getSettleTechDto)
                .orElseThrow(() -> new TechNotFoundException("Tech is not found!"));
    }

    @Override
    public TechnologyEntity getTechnologyEntity(TechnologyDto tech) {

        return Optional.of(tech)
                .map(b -> mp.map(b, TechnologyEntity.class))
                .orElseThrow(() -> new TechNotFoundException("Tech is not found!"));
    }

    @Override
    public ResourcesResponse retrieveResources(TechnologyDto tech, ResourcesResponse resources) {

        if (tech.getMetal() <= resources.getMetal() &&
                tech.getCrystal() <= resources.getCrystal() &&
                tech.getDeuterium() <= resources.getDeuterium()) {

            resources.setMetal(resources.getMetal() - tech.getMetal());
            resources.setCrystal(resources.getCrystal() - tech.getCrystal());
            resources.setDeuterium(resources.getDeuterium() - tech.getDeuterium());

            return resources;
        }
        throw new TechnologyException("Not enough resources!");
    }

    public TechnologyDto getSettleTechDto(TechnologyDto tech) {

        return Optional.of(tech)
                .map(researchesBuildTime::setBuildTime)
                .map(researchesResources::setTechResources)
                .orElseThrow(() -> new TechNotFoundException("tech is not found!"));
    }
}
