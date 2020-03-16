package com.example.ms.swagger;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import java.util.Arrays;
import java.util.List;

@Primary
@Component
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        return Arrays.asList(
                swaggerResource("users", "/api/og-users-ws/v2/api-docs"),
                swaggerResource("resources", "/api/og-resources-ws/v2/api-docs"));
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource sr = new SwaggerResource();
        sr.setName(name);
        sr.setLocation(location);
        sr.setSwaggerVersion("2.0");
        return sr;
    }
}
