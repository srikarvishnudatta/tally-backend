package com.srikar.tally.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FirebaseUserPrincipal {
    String uid;
    String email;
    String name;
}
