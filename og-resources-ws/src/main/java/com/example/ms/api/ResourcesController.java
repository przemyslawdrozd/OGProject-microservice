package com.example.ms.api;

import com.example.ms.exeptions.CreateResourcesException;
import com.example.ms.model.ResourcesResponse;
import com.example.ms.service.ResourcesServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/resources-api")
public class ResourcesController {

    @Value("${service.key.create-resources}")
    private String createValidationKey;

    private final ResourcesServices resourcesServices;

    @Autowired
    public ResourcesController(ResourcesServices resourcesServices) {
        this.resourcesServices = resourcesServices;
    }

    @PostMapping("/{userId}/{create-resources-key}")
    public ResponseEntity<String> createResources(@PathVariable("userId") String userId,
                                                  @PathVariable("create-resources-key") String createResourcesKey) {

        log.info("Validate create resources key: {}", createResourcesKey);

        if (createValidationKey.equals(createResourcesKey)) {
            resourcesServices.createResources(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Resources created");
        }
        log.error("Invalid key: {}", createResourcesKey);
        throw new CreateResourcesException("Resources not created!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResourcesResponse> getResourcesByUserId(@PathVariable("userId") String userId) {

        log.info("Get Resources for: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(resourcesServices.getResourceByUserId(userId));
    }

    @PutMapping("/{userId}/{create-resources-key}")
    public ResponseEntity<String> updateResources(@PathVariable("userId") String userId,
                                                  @PathVariable("create-resources-key") String createResourcesKey,
                                                  @RequestBody ResourcesResponse resources) {

        log.info("Validate create resources key: {}", createResourcesKey);
        
        if (createValidationKey.equals(createResourcesKey)) {
            resourcesServices.updateResources(resources, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Resources updated!");
        }
        log.error("Invalid key: {}", createResourcesKey);
        throw new CreateResourcesException("Resources not created!");
    }
}
