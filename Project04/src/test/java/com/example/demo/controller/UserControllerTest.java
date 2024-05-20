package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private UserRepository userRepository;

    private CartRepository cartRepository;

    private BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        userController = new UserController();
        userRepository = mock(UserRepository.class);
        cartRepository = mock(CartRepository.class);
        encoder = mock(BCryptPasswordEncoder.class);

        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

    }

    @Test
    public void testCreateUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");

        // Mock the encoder.encode method
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");

        ResponseEntity<User> response = userController.createUser(createUserRequest);

        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();

        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals( "thisIsHashed", user.getPassword());
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        User user = TestUtils.createUser();

        // Mock the UserRepository.findById method
        when(userRepository.findById(id)).thenReturn( Optional.of(user));

        ResponseEntity<User> response = userController.findById(id);
        User responseUser = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user.getId(), responseUser.getId());
        assertEquals(user.getUsername(), responseUser.getUsername());
    }
}
