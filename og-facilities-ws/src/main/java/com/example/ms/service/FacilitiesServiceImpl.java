package com.example.ms.service;

import com.example.ms.factory.FacilitiesFactory;
import com.example.ms.model.BuildingResponse;
import com.example.ms.repository.FacilitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FacilitiesServiceImpl implements FacilitiesService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final FacilitiesRepository facilitiesRepository;

    @Autowired
    public FacilitiesServiceImpl(FacilitiesRepository facilitiesRepository) {
        this.facilitiesRepository = facilitiesRepository;
    }

    @Override
    public void createFacilities(String userId) {
        facilitiesRepository.saveAll(FacilitiesFactory.getFacilities(userId));
        LOG.info("Facilities for user: {} saved", userId);
    }

    @Override
    public List<BuildingResponse> getBuildings(String userId) {
        return null;
    }
}
