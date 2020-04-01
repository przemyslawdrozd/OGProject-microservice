package com.example.ms.service;

import com.example.ms.factory.ResourcesFactory;
import com.example.ms.model.ResourcesEntity;
import com.example.ms.model.ResourcesResponse;
import com.example.ms.repository.ResourcesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourcesServiceImplTest {

    @InjectMocks
    private ResourcesServiceImpl resourcesService;

    @Mock
    private ResourcesRepository resourcesRepository;

    @Mock
    private ModelMapper modelMapper;

    private ResourcesResponse expectedResources;

    @Before
    public void before() {
        Optional<ResourcesEntity> resources = Optional.of(ResourcesFactory.getResources("uuid"));
        resources.get().setId(1L);
        expectedResources = new ModelMapper().map(resources.get(), ResourcesResponse.class);

        when(resourcesRepository.findByUserId(any())).thenReturn(resources);
        when(modelMapper.map(any(), any())).thenReturn(expectedResources);
    }

    @Test
    public void shouldGetResourceByUserId() {

        ResourcesResponse actualResources = resourcesService.getResourceByUserId("uuid");

        assertEquals(actualResources.getMetal(), 500);
        assertEquals(actualResources.getCrystal(), 500);
        assertEquals(actualResources.getDeuterium(), 0);
    }
}