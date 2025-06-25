package com.srikar.tally.configuration

data class FirebaseUserPrincipal (
    val uid: String,
    val email: String,
    val name: String
): java.io.Serializable