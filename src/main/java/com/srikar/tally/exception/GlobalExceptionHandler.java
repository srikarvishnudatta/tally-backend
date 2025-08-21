package com.srikar.tally.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex
    ){
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleExpenseException(
            ExpenseNotFoundException ex
    ){
        log.warn("Expense does not exist {}", ex.getMessage());
        var errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGroupException(
            GroupNotFoundException ex
    ){
        log.warn("Group does not exist {}", ex.getMessage());
        var errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(InviteNotValidException.class)
    public ResponseEntity<Map<String, String>> handleGroupException(
            InviteNotValidException ex
    ){
        log.warn("invite does not exist or is not valid {}", ex.getMessage());
        var errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleGroupException(
            UserNotFoundException ex
    ){
        log.warn("User does not exist {}", ex.getMessage());
        var errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
