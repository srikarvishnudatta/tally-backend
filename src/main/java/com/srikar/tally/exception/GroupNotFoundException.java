package com.srikar.tally.exception;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(String message) {
        super(message);
    }
}
