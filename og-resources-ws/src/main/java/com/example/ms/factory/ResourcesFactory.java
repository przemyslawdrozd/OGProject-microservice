package com.example.ms.factory;

import com.example.ms.model.ResourcesEntity;

public abstract class ResourcesFactory {

    public static ResourcesEntity getResources(String userId) {

        ResourcesEntity resourcesEntity = new ResourcesEntity();
        resourcesEntity.setUserId(userId);
        resourcesEntity.setMetal(500);
        resourcesEntity.setCrystal(500);
        resourcesEntity.setDeuterium(0);
        resourcesEntity.setEnergy(0);
        resourcesEntity.setDarkMatter(0);

        return resourcesEntity;
    }
}
