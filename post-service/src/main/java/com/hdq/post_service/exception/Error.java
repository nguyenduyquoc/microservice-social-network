package com.hdq.post_service.exception;

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
