package com.example.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class OgZuulApiGatewayWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OgZuulApiGatewayWsApplication.class, args);
    }

}
