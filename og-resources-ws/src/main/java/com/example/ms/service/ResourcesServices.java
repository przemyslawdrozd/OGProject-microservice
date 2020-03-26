package com.example.ms.service;

import com.example.ms.model.ResourcesResponse;

public interface ResourcesServices {

    void createResources(String userId);

    ResourcesResponse getResourceByUserId(String userId);
}
