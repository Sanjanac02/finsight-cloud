package com.sanjana.finsightcloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice         // central place for handling exceptions from all REST controllers
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ExpenseNotFoundException.class) 
    public ResponseEntity<String> handleExpenseNotFound(
                ExpenseNotFoundException exception) {
        
        return ResponseEntity           //what api sends back to client
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
    }
}
  