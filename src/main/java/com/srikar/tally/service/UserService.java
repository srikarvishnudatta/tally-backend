package com.srikar.tally.service;

import com.srikar.tally.exception.UserNotFoundException;
import com.srikar.tally.model.Users;
import com.srikar.tally.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Users getUserById(String userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
