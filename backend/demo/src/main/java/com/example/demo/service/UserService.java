package com.example.demo.service;

import com.example.demo.api.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository _userRepository;

    public void create(User user) {
        Optional<User> existingUser = _userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        } else {
            _userRepository.save(user);
        }
    }
    public List<User> getAll(){
        return _userRepository.findAll();
    }

    public Optional<User> getById(int userId) {
        return _userRepository.findById(userId);
    }

    public User signUpUser(String email, String password, String firstName, String lastName, String phoneNumber) {
        if (_userRepository.findByEmail(email).isPresent()) {
            throw new EntityExistsException("User with this email already exists.");
        }
        if (_userRepository.findByPhone_number(phoneNumber).isPresent()) {
            throw new EntityExistsException("User with this phone number already exists.");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirst_name(firstName);
        newUser.setLast_name(lastName);
        newUser.setPhone_number(phoneNumber);

        _userRepository.save(newUser);

        return newUser;
    }

    public void update(String userEmail, User updatedUser) {
        Optional<User> existingUser = _userRepository.findByEmail(userEmail);
        if (existingUser.isPresent()) {
            _userRepository.update(userEmail, updatedUser.getFirst_name(), updatedUser.getLast_name(),
                    updatedUser.getPassword(), updatedUser.getPhone_number());
        } else {
            throw new RuntimeException("User not found with this email");
        }
    }

    public void update(User updatedUser) {
        Optional<User> existingUser = _userRepository.findByEmail(updatedUser.getEmail());
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setFirst_name(updatedUser.getFirst_name());
            userToUpdate.setLast_name(updatedUser.getLast_name());
            userToUpdate.setPhone_number(updatedUser.getPhone_number());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setPassword(updatedUser.getPassword());
            _userRepository.save(userToUpdate);
        } else {
            throw new RuntimeException("User not found with this email");
        }
    }

    @Transactional
    public void delete(String userEmail) {
        Optional<User> existingUser = _userRepository.findByEmail(userEmail);
        if (existingUser.isPresent()) {
            _userRepository.deleteByEmail(userEmail);
        } else {
            throw new RuntimeException("User not found with this email");
        }
    }

    public void delete(int id) {
        Optional<User> existingUser = _userRepository.findById(id);
        if (existingUser.isPresent()) {
            _userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with this id");
        }
    }

    public User getUserByCredentials(String email, String password) throws EntityNotFoundException {
        Optional<User> existingUser = _userRepository.findByEmailAndPassword(email, password);
        Optional<User> existingUserWithEmail = _userRepository.findByEmail(email);
        if ( ( email == null || email.isBlank()) && ( password == null || password.isBlank()) ) {
            throw new EntityNotFoundException("Email and password cannot be empty!");
        } else if ( email == null || email.isBlank()) {
            throw new EntityNotFoundException("Email cannot be empty!");
        } else if ( password == null || password.isBlank()) {
            throw new EntityNotFoundException("Password cannot be empty!");
        } else if (existingUser.isPresent()) {
            return existingUser.get();
        } else if (existingUserWithEmail.isPresent()) {
            throw new EntityNotFoundException("Wrong password!");
        } else {
            throw new EntityNotFoundException("No account associated with this email address!");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return _userRepository.findByEmail(email);
    }
}
