package com.example.ms.shared;

import com.example.ms.exeptions.FacilitiesNotFoundException;
import com.example.ms.model.MineBuildingRequest;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "og-facilities-ws", fallbackFactory = FacilitiesFallBackFactory.class)
public interface FacilitiesServiceClient {

    @GetMapping("/facilities-api/levels/{userId}/{create-facilities-key}")
    ResponseEntity<List<MineBuildingRequest>> getFacilitiesLevels(@PathVariable("userId") String userId,
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
    public ResponseEntity<List<MineBuildingRequest>> getFacilitiesLevels(String userId,
                                                                         String createFacilitiesKey) {

        log.error("Error when called: createResources \n{}", throwable.getLocalizedMessage());
        throw new FacilitiesNotFoundException("Facilities not found! wrong userId ?");
    }

}