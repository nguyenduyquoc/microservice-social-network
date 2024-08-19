package com.hdq.profile_service.exceptions;

public class NotFoundEntityException extends Exception {

    public NotFoundEntityException(String objectName, Object id) {
        super(String.format("%s với ID %s không tìm thấy", objectName, id.toString()));
    }
}


