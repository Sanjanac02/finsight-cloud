package com.sanjana.finsightcloud.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sanjana.finsightcloud.exception.UserNotFoundException;
import com.sanjana.finsightcloud.exception.EmailAlreadyExistsException;
import com.sanjana.finsightcloud.exception.InvalidCredentialsException;

@RestControllerAdvice         // central place for handling exceptions from all REST controllers
public class GlobalExceptionHandler {
    
        @ExceptionHandler(ExpenseNotFoundException.class) 
        public ResponseEntity<String> handleExpenseNotFound(
                ExpenseNotFoundException exception) {
        
                return ResponseEntity           //what api sends back to client
                        .status(HttpStatus.NOT_FOUND)
                        .body(exception.getMessage());
        }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationErrors(
                MethodArgumentNotValidException exception) {

                Map<String, String> errors = new HashMap<>();

                exception.getBindingResult().getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errors);
        }

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<String> handleEmailAlreadyExists(
                EmailAlreadyExistsException exception) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(exception.getMessage());
        }

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<String> handleInvalidCredentials(
                InvalidCredentialsException exception) {

                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(exception.getMessage());
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<String> handleUserNotFound(
                        UserNotFoundException exception) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(exception.getMessage());
        }
}
  