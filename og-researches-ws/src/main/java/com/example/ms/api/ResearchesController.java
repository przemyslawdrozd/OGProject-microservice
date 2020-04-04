package com.example.ms.api;

import com.example.ms.exception.technology.CreateResearchesException;
import com.example.ms.model.TechnologyResponse;
import com.example.ms.service.ResearchesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/researches-api")
public class ResearchesController {

    @Value("${service.key.create-resources}")
    private String createFacilitiesKey;

    private final ResearchesService researchesService;

    @Autowired
    public ResearchesController(ResearchesService researchesService) {
        this.researchesService = researchesService;
    }

    @PostMapping("/{userId}/{create-facilities-key}")
    public ResponseEntity<String> createResearches(@PathVariable("userId") String userId,
                                                   @PathVariable("create-facilities-key") String createResearchesKey) {

        log.info("Validate create resources key: {}", createResearchesKey);

        if (this.createFacilitiesKey.equals(createResearchesKey)) {
            researchesService.createResearches(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Researches created!");
        }
        log.error("Invalid key: {}", createResearchesKey);
        throw new CreateResearchesException("Researches not created!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TechnologyResponse>> getResearches(@PathVariable("userId") String userId) {

        log.info("Get researches for: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(researchesService.getResearches(userId));
    }

    @GetMapping("/levelUp/{userId}/{name}")
    public ResponseEntity<String> levelUpTechnology(@PathVariable("userId") String userId,
                                                    @PathVariable("name") String techName) {

        log.info("Try to level up: {}", techName);
        return ResponseEntity.status(HttpStatus.OK).body(researchesService.levelUp(userId, techName));
    }
}
