package com.example.ms.swagger;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import java.util.Arrays;
import java.util.List;

@Primary
@Component
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

    private final Environment env;

    public DocumentationController(Environment env) {
        this.env = env;
    }

    @Override
    public List<SwaggerResource> get() {
        return Arrays.asList(
                swaggerResource("users", env.getProperty("swagger.documentation-url.users")),
                swaggerResource("resources", env.getProperty("swagger.documentation-url.resource")),
                swaggerResource("facilities", env.getProperty("swagger.documentation-url.facilities")),
                swaggerResource("researches", env.getProperty("swagger.documentation-url.researches")));
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource sr = new SwaggerResource();
        sr.setName(name);
        sr.setLocation(location);
        sr.setSwaggerVersion("2.0");
        return sr;
    }
}
