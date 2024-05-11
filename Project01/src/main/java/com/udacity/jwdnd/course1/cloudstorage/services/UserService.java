package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private UserRepository userRepository;
    private HashService hashService;

    public UserService(UserRepository userRepository, HashService hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;
    }
    public int createUser(User user)
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        User newUser = new User(null,user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName());
        return userRepository.insert(newUser);
    }
    public boolean isUsernameAvailable(String username)
    {
        return userRepository.getUserByUsername(username) == null;
    }
    public User getUser(String username)
    {
        return userRepository.getUserByUsername(username);
    }
}
