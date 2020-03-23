package com.example.ms.api;

import com.example.ms.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldCreateUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users-api")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"przemo\",\"password\":\"password\",\"email\":\"przemo@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void shouldNotCreateUserWithShortPassword() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users-api")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"przemo\",\"password\":\"non\",\"email\":\"przemo@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldNotCreateUserWithInvalidEmail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users-api")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"przemo\",\"password\":\"password\",\"email\":\"invalid.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldNotCreateUserWithoutUsername() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/users-api")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"password\",\"email\":\"przemo@gmail.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}