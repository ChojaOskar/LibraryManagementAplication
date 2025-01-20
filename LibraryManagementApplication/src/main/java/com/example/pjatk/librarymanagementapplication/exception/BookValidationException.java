package com.example.pjatk.librarymanagementapplication.exception;

public class BookValidationException extends Exception {
    private String errorMessage;

    public BookValidationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

