package com.example.ms.api;

import com.example.ms.exeptions.CreateResourcesException;
import com.example.ms.model.ResourcesResponse;
import com.example.ms.service.ResourcesServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/resources-api")
public class ResourcesController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Environment env;
    private final ResourcesServices resourcesServices;

    @Autowired
    public ResourcesController(Environment env,
                               ResourcesServices resourcesServices) {
        this.env = env;
        this.resourcesServices = resourcesServices;
    }

    @PostMapping("/{userId}/{create-resources-key}")
    public ResponseEntity<String> createResources(@PathVariable("userId") String userId,
                                @PathVariable("create-resources-key") String createResourcesKey) {

        LOG.info("Validate create resources key: {}", createResourcesKey);
        String key = env.getProperty("service.key.create-resources");

        if (createResourcesKey.equals(key)) {
            resourcesServices.createResources(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Resources created");
        }
        LOG.error("Invalid key: {}", key);
        throw new CreateResourcesException("Resources not created!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResourcesResponse> getResourcesByUserId(@PathVariable("userId") String userId) {

        LOG.info("Get Resources for: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(resourcesServices.getResourceByUserId(userId));
    }
}
