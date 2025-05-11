package com.example.demo.api.controller;

import com.example.demo.api.model.User;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public void create(@RequestBody User user) {
        userService.create(user);
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    //@DeleteMapping("/user/{id}")
    //public void delete(@PathVariable int id) {
    //    userService.delete(id);
    //}

    @DeleteMapping("/user/{email}")
    public void delete(@PathVariable("email") String email) {
        userService.delete(email);
    }


    @PostMapping("/user/login")
    public ResponseEntity<Object> getUserByCredentials(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            User user = userService.getUserByCredentials(email, password);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityNotFoundException(exception.getMessage()));
        }
    }

    @PostMapping("/user/signup")
    public ResponseEntity<Object> signUpUser(@RequestBody Map<String, String> userDetails) {
        String email = userDetails.get("email");
        String password = userDetails.get("password");
        String firstName = userDetails.get("firstName");
        String lastName = userDetails.get("lastName");
        String phoneNumber = userDetails.get("phoneNumber");

        try {
            User newUser = userService.signUpUser(email, password, firstName, lastName, phoneNumber);
            return ResponseEntity.ok(newUser);
        } catch (ConstraintViolationException cve) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception(cve.getConstraintViolations()
                    .stream()
                    .findFirst()
                    .map(ConstraintViolation::getMessage)
                    .orElse("Validation failed.")));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception(exception.getMessage()));
        }
    }

    @PutMapping("/user")
    public ResponseEntity<Object> update(@RequestBody Map<String, String> userDetails) {
        try {
            Optional<User> optionalUser = userService.getUserByEmail(userDetails.get("email"));

            if (optionalUser.isEmpty()) {
                throw new EntityNotFoundException("User not found with email: " + userDetails.get("email"));
            }

            User existingUser = optionalUser.get();

            if (userDetails.containsKey("password")) {
                existingUser.setPassword(userDetails.get("password"));
            }
            if (userDetails.containsKey("firstName")) {
                existingUser.setFirst_name(userDetails.get("firstName"));
            }
            if (userDetails.containsKey("lastName")) {
                existingUser.setLast_name(userDetails.get("lastName"));
            }
            if (userDetails.containsKey("phoneNumber")) {
                existingUser.setPhone_number(userDetails.get("phoneNumber"));
            }

            userService.update(existingUser);

            return ResponseEntity.ok(existingUser);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityNotFoundException(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception(exception.getMessage()));
        }
    }
}