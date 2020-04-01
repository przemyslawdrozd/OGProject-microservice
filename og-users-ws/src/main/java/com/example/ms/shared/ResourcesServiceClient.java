package com.example.ms.shared;

import com.example.ms.exception.CreateUserException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "og-resources-ws", fallbackFactory = ResourcesFallBackFactory.class)
public interface ResourcesServiceClient {

    @PostMapping("/resources-api/{userId}/{create-resources-key}")
    ResponseEntity<String> createResources(@PathVariable("userId") String userId,
                                           @PathVariable("create-resources-key") String createResourcesKey);
}

@Component
class ResourcesFallBackFactory implements FallbackFactory<ResourcesServiceClient> {

    @Override
    public ResourcesServiceClient create(Throwable throwable) {
        return new ResourcesServiceClientCallback(throwable);
    }
}

@Slf4j
class ResourcesServiceClientCallback implements ResourcesServiceClient {

    private final Throwable throwable;

    public ResourcesServiceClientCallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ResponseEntity<String> createResources(String userId, String createResourcesKey) {
        log.error("Error when called: createResources \n{}", throwable.getLocalizedMessage());
        throw new CreateUserException("Resources not created");
    }
}