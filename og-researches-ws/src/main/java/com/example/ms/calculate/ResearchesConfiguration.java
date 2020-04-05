package com.example.ms.calculate;

import com.example.ms.model.TechnologyDto;
import com.example.ms.model.TechnologyEntity;
import com.example.ms.model.TechnologyResponse;
import com.example.ms.model.resources.ResourcesResponse;

public interface ResearchesConfiguration {

    TechnologyResponse configTechResponse(TechnologyEntity entity);

    TechnologyDto configTech(TechnologyEntity tech);

    TechnologyEntity getTechnologyEntity(TechnologyDto tech);

    ResourcesResponse retrieveResources(TechnologyDto tech, ResourcesResponse resources);
}
