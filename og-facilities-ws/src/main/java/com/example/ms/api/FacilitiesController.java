package com.example.ms.api;

import com.example.ms.exception.CreateFacilitiesException;
import com.example.ms.service.FacilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facilities-api")
public class FacilitiesController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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

        LOG.info("Validate create resources key: {}", createFacilitiesKey);

        if (this.createFacilitiesKey.equals(createFacilitiesKey)) {
            facilitiesService.createFacilities(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Facilities created!");
        }
        LOG.error("Invalid key: {}", createFacilitiesKey);
        throw new CreateFacilitiesException("Facilities not created!");
    }

    @GetMapping
    public String getStatus() {
        return "Success";
    }
}
