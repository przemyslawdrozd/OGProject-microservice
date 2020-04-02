package com.example.ms.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/researches-api")
public class ResearchesController {

    @GetMapping
    public String status() {
        return "Connected";
    }
}
