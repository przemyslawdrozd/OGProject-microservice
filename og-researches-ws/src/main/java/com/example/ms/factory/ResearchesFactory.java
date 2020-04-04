package com.example.ms.factory;

import com.example.ms.model.TechnologyEntity;
import java.util.ArrayList;
import java.util.List;

import static com.example.ms.factory.BuildState.*;
import static com.example.ms.factory.TechName.*;

public abstract class ResearchesFactory {

    public static List<TechnologyEntity> getResearches(String userId) {
        List<TechnologyEntity> researches = new ArrayList<>();

        TechnologyEntity energy = new TechnologyEntity();
        energy.setBuildState(BLOCK);
        energy.setName(ENERGY);
        researches.add(energy);

        TechnologyEntity laser = new TechnologyEntity();
        laser.setBuildState(BLOCK);
        laser.setName(LASER);
        researches.add(laser);

        TechnologyEntity ion = new TechnologyEntity();
        ion.setBuildState(BLOCK);
        ion.setName(ION);
        researches.add(ion);

        TechnologyEntity hyper = new TechnologyEntity();
        hyper.setBuildState(BLOCK);
        hyper.setName(HYPER);
        researches.add(hyper);

        TechnologyEntity plasma = new TechnologyEntity();
        plasma.setBuildState(BLOCK);
        plasma.setName(PLASMA);
        researches.add(plasma);

        TechnologyEntity combustion = new TechnologyEntity();
        combustion.setBuildState(BLOCK);
        combustion.setName(COMBUSTION);
        researches.add(combustion);

        TechnologyEntity impulsive = new TechnologyEntity();
        impulsive.setBuildState(BLOCK);
        impulsive.setName(IMPULSIVE);
        researches.add(impulsive);

        TechnologyEntity hyperspace = new TechnologyEntity();
        hyperspace.setBuildState(BLOCK);
        hyperspace.setName(HYPERSPACE);
        researches.add(hyperspace);

        TechnologyEntity spy = new TechnologyEntity();
        spy.setBuildState(BLOCK);
        spy.setName(SPY);
        researches.add(spy);

        TechnologyEntity comp = new TechnologyEntity();
        comp.setBuildState(BLOCK);
        comp.setName(COMP);
        researches.add(comp);

        TechnologyEntity astro = new TechnologyEntity();
        astro.setBuildState(BLOCK);
        astro.setName(ASTRO);
        researches.add(astro);

        TechnologyEntity inter = new TechnologyEntity();
        inter.setBuildState(BLOCK);
        inter.setName(INTER);
        researches.add(inter);

        TechnologyEntity graviton = new TechnologyEntity();
        graviton.setBuildState(BLOCK);
        graviton.setName(GRAVITON);
        researches.add(graviton);

        TechnologyEntity weapon = new TechnologyEntity();
        weapon.setBuildState(BLOCK);
        weapon.setName(WEAPON);
        researches.add(weapon);

        TechnologyEntity shield = new TechnologyEntity();
        shield.setBuildState(BLOCK);
        shield.setName(SHIELD);
        researches.add(shield);

        TechnologyEntity armor = new TechnologyEntity();
        armor.setBuildState(BLOCK);
        armor.setName(ARMOR);
        researches.add(armor);

        for (TechnologyEntity buildingEntity : researches) {
            buildingEntity.setUserId(userId);
            buildingEntity.setLevel(0);
        }

        return researches;
    }
}
