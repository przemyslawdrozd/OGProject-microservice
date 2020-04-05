package com.example.ms.calculate;

import com.example.ms.model.TechnologyEntity;
import com.example.ms.repository.ResearchesRepository;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static com.example.ms.factory.BuildState.*;

@Component
@EnableScheduling
public class ResearchesScheduler {

    private final int SECOND = 1000;
    private final ResearchesRepository researchesRepository;

    public ResearchesScheduler(ResearchesRepository researchesRepository) {
        this.researchesRepository = researchesRepository;
    }

    @Scheduled(fixedDelay = SECOND)
    public void building() {


        researchesRepository.findAllByBuildState(PROGRESS)
                .forEach(entity -> {

                    String newTime = countDownOneSecond(entity.getHowLongToBuild());

                    if (newTime != null) {

                        entity.setHowLongToBuild(newTime);
                        researchesRepository.save(entity);
                    } else {
                        makeLevelUp(entity);
                    }
                });
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

    private void makeLevelUp(TechnologyEntity entity) {

        entity.setLevel(entity.getLevel() + 1);
        entity.setBuildState(READY);
        entity.setHowLongToBuild(null);

        researchesRepository.updateTechStateAsReadyByUserId(entity.getUserId());
        researchesRepository.save(entity);
    }
}
