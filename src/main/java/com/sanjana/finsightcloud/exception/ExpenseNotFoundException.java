package com.sanjana.finsightcloud.exception;

public class ExpenseNotFoundException extends RuntimeException {

    //constructor for our new exception
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
