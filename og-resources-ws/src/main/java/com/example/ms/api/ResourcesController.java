package com.example.ms.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/resources-api")
public class ResourcesController {

    @GetMapping
    public String test() {
        return "test - resources-api";
    }
}
