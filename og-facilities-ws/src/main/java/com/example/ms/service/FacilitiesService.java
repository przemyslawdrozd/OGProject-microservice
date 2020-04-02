package com.example.ms.service;

import com.example.ms.model.BuildingResponse;
import com.example.ms.model.MineBuildingResponse;

import java.util.List;

public interface FacilitiesService {

    void createFacilities(String userId);

    List<BuildingResponse> getBuildings(String userId);

    String levelUp(String userId, String name);

    List<MineBuildingResponse> getMineBuildings(String userId);
}
