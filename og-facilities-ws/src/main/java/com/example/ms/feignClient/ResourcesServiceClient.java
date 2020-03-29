package com.example.ms.feignClient;

import com.example.ms.exception.resources.ResourcesNotFoundException;
import com.example.ms.model.resources.ResourcesResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "og-resources-ws", fallbackFactory = ResourcesFallBackFactory.class)
public interface ResourcesServiceClient {

    @GetMapping("/resources-api/{userId}")
    ResponseEntity<ResourcesResponse> getResourcesByUserId(@PathVariable("userId") String userId);
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
    public ResponseEntity<ResourcesResponse> getResourcesByUserId(String userId) {
        log.error("Error when called: createResources \n{}", throwable.getLocalizedMessage());
        throw new ResourcesNotFoundException("Resources not found! wrong userId ?");
    }
}