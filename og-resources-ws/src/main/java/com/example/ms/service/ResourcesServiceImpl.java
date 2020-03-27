package com.example.ms.service;

import com.example.ms.exeptions.ResourcesNotFoundException;
import com.example.ms.factory.ResourcesFactory;
import com.example.ms.model.ResourcesResponse;
import com.example.ms.repository.ResourcesRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourcesServiceImpl implements ResourcesServices {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final ModelMapper modelMapper;
    private final ResourcesRepository resourcesRepository;

    @Autowired
    public ResourcesServiceImpl(ModelMapper modelMapper,
                                ResourcesRepository resourcesRepository) {
        this.modelMapper = modelMapper;
        this.resourcesRepository = resourcesRepository;
    }

    @Override
    public void createResources(String userId) {

        resourcesRepository.save(ResourcesFactory.getResources(userId));
        LOG.info("Resources for userId: {} saved", userId);
    }

    @Override
    public ResourcesResponse getResourceByUserId(String userId) {

        return resourcesRepository.findByUserId(userId)
                .map(resourcesEntity -> modelMapper.map(resourcesEntity, ResourcesResponse.class))
                .orElseThrow(() -> new ResourcesNotFoundException("Resources not found - Invalid UserId"));
    }
}
