package com.hdq.post_service.exception;

import com.hdq.post_service.core.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<?> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        BaseResponse responseData = new BaseResponse();

        responseData.setStatus(AppErrors.UNCATEGORIZED_EXCEPTION.getCode());
        responseData.setMessage(AppErrors.UNCATEGORIZED_EXCEPTION.getDescription());
        return ResponseEntity.badRequest().body(responseData);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        BaseResponse responseData = new BaseResponse();
        responseData.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.setMessage("Validation Error");
        responseData.setData(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }


    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception){

        AppErrors errorsApp = AppErrors.UNAUTHORIZED;

        BaseResponse responseData = new BaseResponse();
        responseData.setStatus(errorsApp.getCode());
        responseData.setMessage(errorsApp.getDescription());
        responseData.setData(exception.getMessage());

        return ResponseEntity
                .status(errorsApp.getStatusCode())
                .body(responseData);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> handleIOException(IOException exception){

        BaseResponse responseData = new BaseResponse();
        responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseData.setMessage("Error upload image");
        responseData.setData(exception.getMessage());

        return ResponseEntity.status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseData);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Đường dẫn không hợp lệ");
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<?> handleAppException(CustomException ex){
        Error errorResponse = new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
