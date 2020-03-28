package com.example.ms.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facilities-api")
public class FacilitiesController {

    @GetMapping
    public String getStatus() {
        return "Success";
    }
}
