package com.allie.data.service;

import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.MissingResourceException;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@Component
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserFactory factory;
    private UserRepository repository;

    public UserService(UserFactory factory, UserRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {
        User user = factory.createUser(userRequestDTO);
        if(user.getAllieId() != null) {
            User tempUser = repository.insert(user);
            return factory.createUserResponseDTO(tempUser);
        } else {
            logger.debug("User missing required field, received:" + userRequestDTO);
            throw new IllegalArgumentException("User must have an allieId");
        }
    }

    public UserResponseDTO selectUser(String allieId) {
        User tempUser;
        if(allieId != null && !allieId.trim().isEmpty()) {
            tempUser = repository.findByAllieId(allieId);
        } else {
            logger.debug("Get user requires an allieId");
            throw new IllegalArgumentException("Get user requires an allieId");
        }
        if (tempUser != null) {
            return factory.createUserResponseDTO(tempUser);
        } else {
            throw new MissingResourceException("No user found for allieId " + allieId, User.class.getName(), allieId);
        }
    }
}

