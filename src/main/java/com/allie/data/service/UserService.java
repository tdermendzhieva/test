package com.allie.data.service;

import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * inserts a user
     * @param userRequestDTO the user to insert
     * @return returns the newly created user
     */
    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {
        User user = factory.createUser(userRequestDTO);
        if(user.getAllieId() != null) {
            User tempUser = repository.insert(user);
            return factory.createUserResponseDTO(tempUser);
        } else {
            logger.error("User missing required field, received:" + userRequestDTO);
            throw new IllegalArgumentException("User must have an allieId");
        }
    }

    /**
     * Selects a user based on allieId
     * @param allieId the id of the user to retrieve
     * @return returns the user whose allieId matches the input
     */
    public UserResponseDTO selectUser(String allieId) {
        User tempUser;
        if(allieId != null && !allieId.trim().isEmpty()) {
            tempUser = repository.findByAllieId(allieId);
        } else {
            logger.error("Get user requires an allieId");
            throw new IllegalArgumentException("Get user requires an allieId");
        }
        if (tempUser != null) {
            return factory.createUserResponseDTO(tempUser);
        } else {
            throw new MissingResourceException("No user found for allieId " + allieId, User.class.getName(), allieId);
        }
    }

    /**
     * Gets all users based on a format, currently the only supported format is 'list' (case insensitive)
     * throws exceptions for: invalid format, no users found, db exception
     * @param format currently must be 'list'
     * @return returns all User Ids in the requested format
     */
    public List<String> getAllUserIds(String format) {
        if(format.toLowerCase().equals("list")) {
            List<User> users = repository.findAllAllieIds();
            List<String> ids = new ArrayList<>();
            if(users.size() > 0) {
                for (User user : users) {
                    ids.add(user.getAllieId());
                }
                return ids;
            } else {
                throw new MissingResourceException("No users found", User.class.getName(), "");
            }
        } else {
            logger.error("get all users requires a valid format");
            throw new IllegalArgumentException("Get all users requires a valid format");
        }
    }

    /**
     * Updates the user with the given allieId to match the input user.
     * All ommitted fields set to null.
     * @param userRequestDTO The user to update.
     * @return returns the updated user response object
     */
    public UserResponseDTO updateUser(String allieId, UserRequestDTO userRequestDTO) {
        User user = factory.createUser(userRequestDTO);
        String bodyAllieId = user.getAllieId();
        //make sure user provided allieId is not null or empty
        if((bodyAllieId != null && !bodyAllieId.trim().equals("")) && ObjectUtils.nullSafeEquals(bodyAllieId, allieId)) {
            User tempUser = repository.findByAllieId(user.getAllieId());
            if(tempUser != null) {
                user.setDbId(tempUser.getDbId());
                return factory.createUserResponseDTO(repository.save(user));
            } else {
                throw new MissingResourceException("No user found for allieId:" + user.getAllieId(), User.class.getName(), user.getAllieId());
            }
        } else {
            throw new IllegalArgumentException(!ObjectUtils.nullSafeEquals(bodyAllieId, allieId) ? "mismatched allieIds" : "missing required field allieId");
        }
    }
}

