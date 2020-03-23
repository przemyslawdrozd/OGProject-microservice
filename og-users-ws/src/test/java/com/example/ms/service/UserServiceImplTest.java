package com.example.ms.service;

import com.example.ms.exception.CreateUserException;
import com.example.ms.model.UserEntity;
import com.example.ms.model.UserRequest;
import com.example.ms.model.UserResponse;
import com.example.ms.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserEntity userEntity;

    private UserRequest userRequest;

    private ModelMapper modelMapper;

    @Before
    public void before() {

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        String userId = UUID.randomUUID().toString();

        when(passwordEncoder.encode(any())).thenReturn("encryptedpassword");

        this.userRequest = new UserRequest("przemo", "password", "username@gmail.com");
        this.userEntity = modelMapper.map(userRequest, UserEntity.class);
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(passwordEncoder.encode("encryptedpassword"));
    }

    @Test
    public void shouldCreateUser() {

        UserResponse expectedUser = modelMapper.map(userEntity, UserResponse.class);

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserResponse actualUser = userService.createUser(userRequest);

        assertEquals(expectedUser.getUserId(), actualUser.getUserId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test(expected = CreateUserException.class)
    public void shouldNotCreateUserWhenEmailExists() {

        when(userRepository.existsByEmail(any())).thenReturn(true);
        userService.createUser(userRequest);
    }

    @Test
    public void shouldGetUserDetailsByEmail() {

        UserResponse expectedUser = modelMapper.map(userEntity, UserResponse.class);

        when(userRepository.findByEmail(any())).thenReturn(userEntity);

        UserResponse actualUser = userService.getUserDetailsByEmail(userRequest.getEmail());

        assertEquals(expectedUser.getUserId(), actualUser.getUserId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldNotGetUserDetailsByEmailWhenNotFound() {

        when(userRepository.findByEmail(any())).thenReturn(null);
        userService.getUserDetailsByEmail(userRequest.getEmail());
    }

    @Test
    public void shouldLoadUserByUsername() {

        when(userRepository.findByEmail(any())).thenReturn(userEntity);
        UserDetails userDetailsActual = userService.loadUserByUsername(userEntity.getEmail());

        assertEquals(userEntity.getEmail(), userDetailsActual.getUsername());
    }

}