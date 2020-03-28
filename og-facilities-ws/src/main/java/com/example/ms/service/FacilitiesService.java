package com.example.ms.service;

import com.example.ms.model.BuildingResponse;

import java.util.List;

public interface FacilitiesService {

    void createFacilities(String userId);

    List<BuildingResponse> getBuildings(String userId);
}
