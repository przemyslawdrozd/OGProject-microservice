package com.example.ms.repository;

import com.example.ms.model.ResourcesEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ResourcesRepositoryTest {

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Test
    public void findByUserId() {
        List<ResourcesEntity> all = resourcesRepository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    public void shouldReturnResources() {

        ResourcesEntity actualResources = resourcesRepository.findByUserId("c4aeb7bf-0fa7-4ebd-bc11-7ef3db30f2e1").get();

        assertEquals(500, actualResources.getMetal());
        assertEquals(500, actualResources.getCrystal());
        assertEquals(0, actualResources.getDeuterium());
    }
}