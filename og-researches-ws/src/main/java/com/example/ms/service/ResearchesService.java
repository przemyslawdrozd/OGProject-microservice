package com.example.ms.service;

import com.example.ms.model.TechnologyResponse;

import java.util.List;

public interface ResearchesService {

    void createResearches(String userId);

    List<TechnologyResponse> getResearches(String userId);

    String levelUp(String userId, String name);
}
