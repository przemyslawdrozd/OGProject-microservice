package com.example.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class OgFacilitiesWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OgFacilitiesWsApplication.class, args);
    }

}
