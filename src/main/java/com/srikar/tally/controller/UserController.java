package com.srikar.tally.controller;


import com.srikar.tally.configuration.FirebaseUserPrincipal;
import com.srikar.tally.dto.UserRequestDto;
import com.srikar.tally.repository.UserRepository;
import com.srikar.tally.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/")
    public String getString(){return "hi user, this is free access";}

    @PostMapping("/create-if-not-exists")
    public ResponseEntity<?> createUser(
            @AuthenticationPrincipal FirebaseUserPrincipal firebaseUserPrincipal
            , @RequestBody UserRequestDto dto){
        try {
            var uid = firebaseUserPrincipal.getUid();
            var email = firebaseUserPrincipal.getEmail();
            var firstName = dto.getFirstName();
            var lastName = dto.getLastName();
            return repo.findById(uid)
                    .map(existing -> ResponseEntity.status(HttpStatus.CONFLICT).body(existing.getFirstName()))
                    .orElseGet(() -> {
                        var newUser = Users.builder()
                                .id(uid)
                                .email(email)
                                .firstName(firstName)
                                .lastName(lastName)
                                .build();
                        var saved = repo.save(newUser);
                        return ResponseEntity.status(HttpStatus.CREATED).body(saved.getFirstName());
                    });
        }catch (Exception e){
            log.info("Some error has occurred {}", e.getMessage());
            return ResponseEntity.status(400).body(null);
        }
    }
}
