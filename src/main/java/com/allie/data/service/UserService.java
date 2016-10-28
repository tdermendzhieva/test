package com.allie.data.service;

import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {
        User user = factory.createUser(userRequestDTO);
        if(user.getAllieId() != null) {
            try {
                User tempUser = repository.insert(user);
                return factory.createUserResponseDTO(tempUser);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw e;
            }
        } else {
            logger.error("User missing required field, received:" + userRequestDTO);
            throw new IllegalArgumentException("User must have an allieId");
        }
    }

    public UserResponseDTO selectUser(String allieId) {
        User tempUser;
        if(allieId != null && !allieId.trim().isEmpty()) {
            try {
                tempUser = repository.findByAllieId(allieId);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw e;
            }
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
     * gets all users based on a format, curretly the only suported format is 'list' (case insensitive)
     * throws exceptions for: invalid format, no users found, db exception
     * @param format currently must be 'list'
     * @return
     */
    public List<String> getAllUserIds(String format) {
        if(format.toLowerCase().equals("list")) {
            try {
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
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw e;
            }
        } else {
            logger.error("get all users requires a valid format");
            throw new IllegalArgumentException("Get all users requires a valid format");
        }
    }
}

