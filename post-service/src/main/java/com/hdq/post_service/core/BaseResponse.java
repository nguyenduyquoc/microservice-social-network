package com.hdq.post_service.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class BaseResponse implements Serializable {

    private int status;
    private String message;
    private Object data;

    public BaseResponse() {
        this.status = 200;
        this.message = "success";
    }

    public BaseResponse(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(HttpStatus httpStatus, Object data) {
        this.status = httpStatus.value();
        this.data = data;
        this.message = httpStatus.name();
    }

    public static BaseResponse builder() {
        return new BaseResponse();
    }

    public static BaseResponse success(Object data) {
        return new BaseResponse(HttpStatus.OK, data);
    }

    public static BaseResponse success(String message, Object data) {
        return new BaseResponse(HttpStatus.OK.value(), data, message);
    }

    public static BaseResponse created(Object data) {
        return new BaseResponse(HttpStatus.CREATED, data);
    }

    public static BaseResponse noContent() {
        return new BaseResponse(HttpStatus.NO_CONTENT, null);
    }

    public static BaseResponse notFound() {
        return new BaseResponse(HttpStatus.NOT_FOUND, null);
    }

    public static BaseResponse notAllowed() {
        return new BaseResponse(HttpStatus.METHOD_NOT_ALLOWED, null);
    }

    public static BaseResponse error(Exception e) {
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public static BaseResponse unauthorized(String message) {
        return BaseResponse.builder().setStatus(HttpStatus.UNAUTHORIZED.value()).setMessage(message);
    }

    public static BaseResponse badRequest(Object data) {
        return new BaseResponse(HttpStatus.BAD_REQUEST, data);
    }

    public BaseResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public BaseResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public static BaseResponse throwException(Exception e) {
        BaseResponse response = new BaseResponse(HttpStatus.BAD_REQUEST, null);
        response.setMessage(e.getMessage());
        return response;
    }


}
