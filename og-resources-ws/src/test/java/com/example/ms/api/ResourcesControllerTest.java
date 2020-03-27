package com.example.ms.api;

import com.example.ms.factory.ResourcesFactory;
import com.example.ms.model.ResourcesEntity;
import com.example.ms.model.ResourcesResponse;
import com.example.ms.service.ResourcesServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ResourcesController.class)
public class ResourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourcesServices resourcesServices;

    @Mock
    private Environment env;

    private ResourcesResponse resourcesResponse;

    @Before
    public void before() {
        ResourcesEntity resourcesEntity = ResourcesFactory.getResources(UUID.randomUUID().toString());
        this.resourcesResponse = new ModelMapper().map(resourcesEntity, ResourcesResponse.class);
    }

    @Test
    public void shouldCreateResources() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/resources-api/userId/75e59732-6e7b-11ea-bc55-0242ac130003")
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void shouldNotCreateResources() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/resources-api/userId/invalidKey")
                .accept(APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void shouldGetResourcesByUserId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/resources-api/userId")
                .accept(APPLICATION_JSON);

        when(resourcesServices.getResourceByUserId(any())).thenReturn(resourcesResponse);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{metal:500,crystal:500,deuterium:0,energy:0,darkMatter:0}"))
                .andReturn();
    }
}