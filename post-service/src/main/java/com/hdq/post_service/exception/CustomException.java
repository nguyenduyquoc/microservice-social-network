package com.hdq.post_service.exception;

import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Getter
public class CustomException extends RuntimeException {

    private final String message;

    public CustomException(MessageSource messageSource, String message, Object... args) {
        super(message);
        this.message = messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage() {
        return message;
    }
}
