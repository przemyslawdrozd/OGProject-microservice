package com.example.ms.shared;

import com.example.ms.exception.technology.TechnologyException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "og-facilities-ws", fallbackFactory = FacilitiesFallBackFactory.class)
public interface FacilitiesServiceClient {

    @GetMapping("/facilities-api/research/{userId}")
    ResponseEntity<Integer> getResearchLvl(@PathVariable("userId") String userId);
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
    public ResponseEntity<Integer> getResearchLvl(String userId) {
        log.error("Error when called: getResearchLvl \n{}", throwable.getLocalizedMessage());
        throw new TechnologyException("Research building level not returned");
    }
}