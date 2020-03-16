package com.example.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OgResourcesWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OgResourcesWsApplication.class, args);
    }

}
