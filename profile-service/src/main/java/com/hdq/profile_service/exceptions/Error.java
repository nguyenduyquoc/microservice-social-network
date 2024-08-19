package com.hdq.profile_service.exceptions;

public class Error {
    private final int status;
    private final String message;

    public Error(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
