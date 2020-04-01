package com.example.ms.shared;

import com.example.ms.exception.CreateUserException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "og-facilities-ws", fallbackFactory = FacilitiesFallBackFactory.class)
public interface FacilitiesServiceClient {

    @PostMapping("/facilities-api/{userId}/{create-facilities-key}")
    ResponseEntity<String> createFacilities(@PathVariable("userId") String userId,
                                            @PathVariable("create-facilities-key") String createFacilitiesKey);
}

@Component
class FacilitiesFallBackFactory implements FallbackFactory<FacilitiesServiceClient> {

    @Override
    public FacilitiesServiceClient create(Throwable throwable) {
        return new FacilitiesServiceClientCallback(throwable);
    }
}

@Slf4j
class FacilitiesServiceClientCallback implements FacilitiesServiceClient {

    private final Throwable throwable;

    public FacilitiesServiceClientCallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ResponseEntity<String> createFacilities(String userId, String createResourcesKey) {
        log.error("Error when called: createFacilities \n{}", throwable.getLocalizedMessage());
        throw new CreateUserException("Facilities not created");
    }
}