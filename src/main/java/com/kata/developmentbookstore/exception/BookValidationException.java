package com.kata.developmentbookstore.exception;

import java.util.List;

public class BookValidationException extends RuntimeException {

    private List<String> errors;

    public BookValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public BookValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<String> getErrors() {
        return errors;
    }
}
