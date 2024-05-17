package com.example.Project_CRUD.controllers;

import com.example.Project_CRUD.models.User;
import com.example.Project_CRUD.repositories.UserRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Get a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new user
    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User newUser) {
        User user = userRepository.save(newUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

            // Assuming User has fields such as name, email etc.
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
