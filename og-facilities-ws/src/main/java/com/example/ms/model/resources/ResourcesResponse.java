package com.example.ms.model.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ResourcesResponse {

    private int metal;
    private int crystal;
    private int deuterium;
    private int energy;
    private int darkMatter;
}
