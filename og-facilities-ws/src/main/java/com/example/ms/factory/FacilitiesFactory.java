package com.example.ms.factory;

import com.example.ms.model.BuildingEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class FacilitiesFactory {

    public static List<BuildingEntity> getFacilities(String userId) {
        List<BuildingEntity> facilities = new ArrayList<>();

        BuildingEntity metal = new BuildingEntity();
        metal.setBuildState("READY"); // For buildings ready to build
        metal.setName("metal");
        facilities.add(metal);

        BuildingEntity crystal = new BuildingEntity();
        crystal.setName("crystal");
        crystal.setBuildState("READY");
        facilities.add(crystal);

        BuildingEntity deuterium = new BuildingEntity();
        deuterium.setName("deuterium");
        deuterium.setBuildState("READY");
        facilities.add(deuterium);

        BuildingEntity solar = new BuildingEntity();
        solar.setName("solar");
        solar.setBuildState("READY");
        facilities.add(solar);

        BuildingEntity mStorage = new BuildingEntity();
        mStorage.setName("metal_storage");
        mStorage.setBuildState("READY");
        facilities.add(mStorage);

        BuildingEntity cStorage = new BuildingEntity();
        cStorage.setName("crystal_storage");
        cStorage.setBuildState("READY");
        facilities.add(cStorage);

        BuildingEntity dStorage = new BuildingEntity();
        dStorage.setName("deuterium_storage");
        dStorage.setBuildState("READY");
        facilities.add(dStorage);

        BuildingEntity robotic = new BuildingEntity();
        robotic.setName("robotic");
        robotic.setBuildState("READY");
        facilities.add(robotic);

        BuildingEntity research = new BuildingEntity();
        research.setName("research");
        research.setBuildState("READY");
        facilities.add(research);

        BuildingEntity fusion = new BuildingEntity();
        fusion.setName("fusion"); // needs 5 lvl deuterium
        fusion.setBuildState("BLOCK"); // state block for buildings that need some requirements
        facilities.add(fusion);

        BuildingEntity shipyard = new BuildingEntity();
        shipyard.setName("shipyard"); // requirements robotic lvl 2
        shipyard.setBuildState("BLOCK");
        facilities.add(shipyard);

        BuildingEntity nanite = new BuildingEntity();
        nanite.setName("nanite"); // needs 10 lvl robotic, computer
        nanite.setBuildState("BLOCK");
        facilities.add(nanite);

        BuildingEntity terraform = new BuildingEntity();
        terraform.setName("terraform"); // needs 12 research, 1 nanite
        terraform.setBuildState("BLOCK");
        facilities.add(terraform);

        for (BuildingEntity buildingEntity : facilities) {
            buildingEntity.setUserId(userId);
            buildingEntity.setLevel(0);
        }

        return facilities;
    }
}
