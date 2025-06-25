package com.srikar.tally.users;

import java.util.Optional;

public interface UserService {
    Optional<Users> findUserById(String userId);
    Optional<Users> findUserByEmail(String email);
}
