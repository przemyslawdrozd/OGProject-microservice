package com.example.ms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class FacilitiesServiceImplTest {

    @InjectMocks
    private FacilitiesServiceImpl facilitiesService;

    @Test
    public void createFacilities() {

        facilitiesService.createFacilities("userid");


    }
}