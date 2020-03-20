package com.example.ms.service;

import com.example.ms.exception.CreateUserException;
import com.example.ms.model.UserEntity;
import com.example.ms.model.UserRequest;
import com.example.ms.model.UserResponse;
import com.example.ms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userRequestEntity = modelMapper.map(userRequest, UserEntity.class);
        userRequestEntity.setEncryptedPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRequestEntity.setUserId(UUID.randomUUID().toString());

        if (!userRepository.existsByEmail(userRequestEntity.getEmail())) {
            LOG.info("Save new user");
            UserEntity userResponseEntity = userRepository.save(userRequestEntity);
            return modelMapper.map(userResponseEntity, UserResponse.class);
        }

        throw new CreateUserException("User exists!");
    }

    @Override
    public UserResponse getUserDetailsByEmail(String email) {
        return null;
    }

    @Override
    public UserResponse getUserByUserId(String userId) {
        return null;
    }
}
