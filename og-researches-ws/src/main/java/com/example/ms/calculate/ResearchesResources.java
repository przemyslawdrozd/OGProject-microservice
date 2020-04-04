package com.example.ms.calculate;

import com.example.ms.exception.technology.TechNotFoundException;

import com.example.ms.model.TechnologyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.example.ms.factory.TechName.*;

@Component
public class ResearchesResources {

    @Value("${rate.neededResources}")
    private int neededResourcesRate;

    public TechnologyDto setTechResources(TechnologyDto tech) {
        TechnologyDto technologyDto = tech.getLevel() == 0 ? defaultResources(tech) : calculateResources(tech);

        return upgradeTech(technologyDto);
    }

    private TechnologyDto upgradeTech(TechnologyDto tech) {

        // TODO facilities service client
        // lab lvl == 1 : set unblock ENERGY, LASER, COMP
        if (false) {

        }

        return tech;

    }

    private TechnologyDto calculateResources(TechnologyDto technologyDto) {

        TechnologyDto tech = defaultResources(technologyDto);

        int metalNextLevel = tech.getMetal()  * neededResourcesRate;
        int crystalNextLevel = tech.getCrystal() *  neededResourcesRate;
        int deuteriumNextLevel = tech.getDeuterium() * neededResourcesRate;

        tech.setMetal(metalNextLevel);
        tech.setCrystal(crystalNextLevel);
        tech.setDeuterium(deuteriumNextLevel);

        return tech;
    }

    private TechnologyDto defaultResources(TechnologyDto tech) {

        switch (tech.getName()) {

            case ENERGY:
                tech.setMetal(0);
                tech.setCrystal(800);
                tech.setDeuterium(400);
                return tech;

            case LASER:
                tech.setMetal(200);
                tech.setCrystal(100);
                tech.setDeuterium(0);
                return tech;

            case ION:
                tech.setMetal(1000);
                tech.setCrystal(300);
                tech.setDeuterium(100);
                return tech;

            case HYPER:
                tech.setMetal(0);
                tech.setCrystal(4000);
                tech.setDeuterium(2000);
                return tech;

            case PLASMA:
                tech.setMetal(200);
                tech.setCrystal(4000);
                tech.setDeuterium(1000);
                return tech;

            case COMBUSTION:
                tech.setMetal(400);
                tech.setCrystal(0);
                tech.setDeuterium(600);
                return tech;

            case IMPULSIVE:
                tech.setMetal(2000);
                tech.setCrystal(4000);
                tech.setDeuterium(600);
                return tech;

            case HYPERSPACE:
                tech.setMetal(10000);
                tech.setCrystal(20000);
                tech.setDeuterium(6660);
                return tech;

            case SPY:
                tech.setMetal(2000);
                tech.setCrystal(1000);
                tech.setDeuterium(200);
                return tech;

            case COMP:
                tech.setMetal(0);
                tech.setCrystal(400);
                tech.setDeuterium(600);
                return tech;

            case ASTRO:
                tech.setMetal(4000);
                tech.setCrystal(8000);
                tech.setDeuterium(4000);
                return tech;

            case INTER:
                tech.setMetal(100_000);
                tech.setCrystal(100_000);
                tech.setDeuterium(100_000);
                return tech;

            case GRAVITON:
                tech.setMetal(0);
                tech.setCrystal(0);
                tech.setDeuterium(0);
                return tech;

            case WEAPON:
                tech.setMetal(800);
                tech.setCrystal(200);
                tech.setDeuterium(0);
                return tech;

            case SHIELD:
                tech.setMetal(200);
                tech.setCrystal(600);
                tech.setDeuterium(0);
                return tech;

            case ARMOR:
                tech.setMetal(1000);
                tech.setCrystal(0);
                tech.setDeuterium(0);
                return tech;

            default:
                throw new TechNotFoundException(tech.getName() + " unknown!");
        }
    }
}
