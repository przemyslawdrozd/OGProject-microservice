package com.example.ms.calculate;

import com.example.ms.model.BuildingEntity;
import com.example.ms.repository.FacilitiesRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static com.example.ms.factory.BuildState.*;

@Log4j2
@Component
@EnableScheduling
public class FacilitiesScheduler {

    private final int SECOND = 1000;
    private final FacilitiesRepository facilitiesRepository;

    public FacilitiesScheduler(FacilitiesRepository facilitiesRepository) {
        this.facilitiesRepository = facilitiesRepository;
    }

    @Scheduled(fixedDelay = SECOND)
    public void building() {

        List<BuildingEntity> progressFacilities = facilitiesRepository.findAllByBuildState(PROGRESS);
        for (BuildingEntity entity: progressFacilities) {

            String newTime = countDownOneSecond(entity.getHowLongToBuild());
            if (newTime != null) {

                entity.setHowLongToBuild(newTime);
                facilitiesRepository.save(entity);
                continue;
            }
            makeLevelUp(entity);
        }
    }

    private String countDownOneSecond(String howLongToBuild) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            long newTime = dateFormat.parse(howLongToBuild).getTime() - SECOND;
            return newTime > SECOND ?
                    DurationFormatUtils.formatDurationHMS(newTime).replace(".000", "") : null;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void makeLevelUp(BuildingEntity entity) {

        entity.setLevel(entity.getLevel() + 1);
        entity.setBuildState(READY);
        entity.setHowLongToBuild(null);

        facilitiesRepository.updateBuildStateAsReadyByUserId(entity.getUserId());
        facilitiesRepository.save(entity);
    }
}
