package com.srikar.tally.users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/")
    public String getString(){return "hi user, this is free access";}

    @PostMapping("/create-if-not-exists")
    public ResponseEntity<?> createUser(@RequestBody UserDto dto){
        try {
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(dto.getToken());
            var uid = token.getUid();
            var email = token.getEmail();
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
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }

    }
}
