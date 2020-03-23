package com.example.ms.repository;

import com.example.ms.model.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindAll() {

        List<UserEntity> all = userRepository.findAll();
        assertEquals(4, all.size());
    }

    @Test
    public void shouldReturnTrueIfUserExistsByEmail() {

        boolean actualBoolean = userRepository.existsByEmail("username@gmail.com");
        assertTrue(actualBoolean);
    }

    @Test
    public void shouldReturnFalseIfUserDoesNotExistByEmail() {

        boolean actualBoolean = userRepository.existsByEmail("wrong@gmail.com");
        assertFalse(actualBoolean);
    }

    @Test
    public void shouldRetrieveUserByEmail() {

        UserEntity userActual = userRepository.findByEmail("username2@gmail.com");
        assertEquals("username2@gmail.com", userActual.getEmail());
    }

    @Test
    public void shouldReturnFalseIfUserByEmailNotFound() {

        UserEntity actualUser = userRepository.findByEmail("wrong@gmail.com");
        assertNull(actualUser);
    }

}