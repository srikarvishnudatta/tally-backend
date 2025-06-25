package com.srikar.tally.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/create-if-not-exists")
    public ResponseEntity<Users> createUser(@RequestBody UserDto dto){
        return repo.findById(dto.getUid())
                .map(existing -> ResponseEntity.status(HttpStatus.CONFLICT).body(existing))
                .orElseGet(() -> {
                   var newUser = Users.builder()
                           .id(dto.getUid())
                           .email(dto.getEmail())
                           .firstName(dto.getFirstName())
                           .lastName(dto.getLastName())
                           .build();
                   var saved = repo.save(newUser);
                   return ResponseEntity.status(HttpStatus.CREATED).body(saved);
                });
    }
}
