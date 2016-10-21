package com.allie.data.service;

import com.allie.data.dto.UserDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    public User insertUser(UserDTO userDTO) {
        User user = factory.createUser(userDTO);
        User toReturn;

        if(user.getAllieId() != null) {
            toReturn = repository.insert(user);
        } else {
            logger.debug("User Movement missing required field, received:" + userDTO);
            throw(new IllegalArgumentException("User must have an allieId"));
        }

        return toReturn;
    }
}
