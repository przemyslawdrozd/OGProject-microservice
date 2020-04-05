package com.example.ms.shared;

import com.example.ms.exception.CreateUserException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "og-researches-ws", fallbackFactory = ResearchesFallBackFactory.class)
public interface ResearchesServiceClient {

    @PostMapping("/researches-api/{userId}/{create-facilities-key}")
    ResponseEntity<String> createResearches(@PathVariable("userId") String userId,
                                            @PathVariable("create-facilities-key") String createFacilitiesKey);
}

@Component
class ResearchesFallBackFactory implements FallbackFactory<ResearchesServiceClient> {

    @Override
    public ResearchesServiceClient create(Throwable throwable) {
        return new ResearchesServiceClientCallback(throwable);
    }
}

@Slf4j
class ResearchesServiceClientCallback implements ResearchesServiceClient {

    private final Throwable throwable;

    public ResearchesServiceClientCallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ResponseEntity<String> createResearches(String userId, String createResourcesKey) {
        log.error("Error when called: createResearches \n{}", throwable.getLocalizedMessage());
        throw new CreateUserException("Researches not created");
    }
}
