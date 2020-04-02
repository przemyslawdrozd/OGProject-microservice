package com.example.ms.api;

import com.example.ms.exception.buildings.CreateFacilitiesException;
import com.example.ms.exception.buildings.FacilitiesException;
import com.example.ms.model.BuildingResponse;
import com.example.ms.model.MineBuildingResponse;
import com.example.ms.service.FacilitiesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/facilities-api")
public class FacilitiesController {

    @Value("${service.key.create-resources}")
    private String createFacilitiesKey;

    private final FacilitiesService facilitiesService;

    @Autowired
    public FacilitiesController(FacilitiesService facilitiesService) {
        this.facilitiesService = facilitiesService;
    }

    @PostMapping("/{userId}/{create-facilities-key}")
    public ResponseEntity<String> createFacilities(@PathVariable("userId") String userId,
                                                   @PathVariable("create-facilities-key") String createFacilitiesKey) {

        log.info("Validate create resources key: {}", createFacilitiesKey);

        if (this.createFacilitiesKey.equals(createFacilitiesKey)) {
            facilitiesService.createFacilities(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Facilities created!");
        }
        log.error("Invalid key: {}", createFacilitiesKey);
        throw new CreateFacilitiesException("Facilities not created!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BuildingResponse>> getFacilities(@PathVariable("userId") String userId) {

        log.info("Get facilities for: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(facilitiesService.getBuildings(userId));
    }

    @GetMapping("/levelUp/{userId}/{name}")
    public ResponseEntity<String> levelUpBuilding(@PathVariable("userId") String userId,
                                                  @PathVariable("name") String name) {

        log.info("Try to level up: {}", name);
        return ResponseEntity.status(HttpStatus.OK).body(facilitiesService.levelUp(userId, name));
    }

    @GetMapping("/levels/{userId}/{create-facilities-key}")
    public ResponseEntity<List<MineBuildingResponse>> getFacilitiesLevels(@PathVariable("userId") String userId,
                                                  @PathVariable("create-facilities-key") String createFacilitiesKey) {

        log.trace("Facilities retrieving for update resources");

        if (this.createFacilitiesKey.equals(createFacilitiesKey)) {
            return ResponseEntity.status(HttpStatus.OK).body(facilitiesService.getMineBuildings(userId));
        }
        log.error("Wrong valid key");
        throw new FacilitiesException("Invalid key");
    }
}
