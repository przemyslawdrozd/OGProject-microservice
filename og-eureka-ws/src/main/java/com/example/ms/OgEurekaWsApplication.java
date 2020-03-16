package com.example.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class OgEurekaWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OgEurekaWsApplication.class, args);
    }

}
