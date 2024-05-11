package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private UserService userService;
    public AuthorizationService(UserService userService) {
        this.userService = userService;
    }
    public boolean signUp(User user) {
        if (user == null) {
            return false;
        }
        if (userService.getUser(user.getUsername()) != null) {
            return false;
        }
        this.userService.createUser(user);
        return true;
    }
}
