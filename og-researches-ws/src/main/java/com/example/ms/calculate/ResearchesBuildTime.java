package com.example.ms.calculate;

import com.example.ms.exception.technology.TechNotFoundException;
import com.example.ms.model.TechnologyDto;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.example.ms.factory.TechName.*;
import static com.example.ms.factory.TechName.ARMOR;

@Component
public class ResearchesBuildTime {

    @Value("${rate.buildTime}")
    private int buildTimeRate;

    public TechnologyDto setBuildTime(TechnologyDto building) {
        return building.getLevel() == 0 ? defaultBuildTime(building) : calculateBuildTime(building);
    }

    private TechnologyDto calculateBuildTime(TechnologyDto tech) {

        TechnologyDto building = defaultBuildTime(tech);
        String buildTime = building.getBuildTime();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = format.parse(buildTime);
            long newTime = (date.getTime() * buildTimeRate);

            String newBuildTime = DurationFormatUtils.formatDurationHMS(newTime).replace(".000", "");
            building.setBuildTime(newBuildTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return building;
    }

    private TechnologyDto defaultBuildTime(TechnologyDto tech) {

        switch (tech.getName()) {

            case ENERGY:
                tech.setBuildTime("00:01:25");
                return tech;

            case LASER:
                tech.setBuildTime("00:01:30");
                return tech;

            case ION:
                tech.setBuildTime("00:06:30");
                return tech;

            case HYPER:
                tech.setBuildTime("00:10:00");
                return tech;

            case PLASMA:
                tech.setBuildTime("00:20:00");
                return tech;

            case COMBUSTION:
                tech.setBuildTime("00:02:00");
                return tech;

            case IMPULSIVE:
                tech.setBuildTime("00:30:00");
                return tech;

            case HYPERSPACE:
                tech.setBuildTime("00:30:00");
                return tech;

            case SPY:
                tech.setBuildTime("00:05:00");
                return tech;

            case COMP:
                tech.setBuildTime("00:02:00");
                return tech;

            case ASTRO:
                tech.setBuildTime("01:00:00");
                return tech;

            case INTER:
                tech.setBuildTime("24:00:00");
                return tech;

            case GRAVITON:
                tech.setBuildTime("00:00:01");
                return tech;

            case WEAPON:
                tech.setBuildTime("00:05:00");
                return tech;

            case SHIELD:
                tech.setBuildTime("00:04:00");
                return tech;

            case ARMOR:
                tech.setBuildTime("00:05:00");
                return tech;

            default:
                throw new TechNotFoundException(tech.getName() + " unknown!");
        }
    }
}
