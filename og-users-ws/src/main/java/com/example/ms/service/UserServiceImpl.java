package com.example.ms.service;

import com.example.ms.exception.CreateUserException;
import com.example.ms.model.UserEntity;
import com.example.ms.model.UserRequest;
import com.example.ms.model.UserResponse;
import com.example.ms.repository.UserRepository;
import com.example.ms.shared.ResourcesServiceClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Environment env;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResourcesServiceClient resourcesServiceClient;

    @Autowired
    public UserServiceImpl(Environment env, UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ResourcesServiceClient resourcesServiceClient) {
        this.env = env;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.resourcesServiceClient = resourcesServiceClient;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userRequestEntity = modelMapper.map(userRequest, UserEntity.class);
        userRequestEntity.setEncryptedPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRequestEntity.setUserId(UUID.randomUUID().toString());

        if (!userRepository.existsByEmail(userRequestEntity.getEmail())) {
            LOG.info("Save new user: {}", userRequestEntity.getUserId());

            resourcesServiceClient.createResources(userRequestEntity.getUserId(),
                    env.getProperty("service.key.create-resources"));

            UserEntity userResponseEntity = userRepository.save(userRequestEntity);
            return modelMapper.map(userResponseEntity, UserResponse.class);
        }

        throw new CreateUserException("User exists!");
    }

    @Override
    public UserResponse getUserDetailsByEmail(String email) {
        LOG.info("getUserDetailsByEmail {} in database", email);
        UserEntity userEntityResponse = userRepository.findByEmail(email);

        if (userEntityResponse != null){
            return new ModelMapper().map(userEntityResponse, UserResponse.class);
        }
        throw new UsernameNotFoundException(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOG.info("loadUserByUsername: {} in database", email);
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity != null){
            return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                    true, true, true, true, new ArrayList<>());
        }
        throw new UsernameNotFoundException(email);
    }
}
