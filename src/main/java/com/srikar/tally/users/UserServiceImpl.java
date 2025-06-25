package com.srikar.tally.users;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<Users> findUserById(String userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<Users> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
