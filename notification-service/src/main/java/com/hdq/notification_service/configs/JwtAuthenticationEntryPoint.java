package com.hdq.notification_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdq.notification_service.core.BaseResponse;
import com.hdq.notification_service.exception.AppErrors;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        AppErrors errorsApp = AppErrors.UNAUTHENTICATED;

        response.setStatus(errorsApp.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        BaseResponse responseData = new BaseResponse();

        responseData.setStatus(errorsApp.getCode());
        responseData.setMessage("Error");
        responseData.setData(errorsApp.getDescription());

        ObjectMapper objectMapper = new ObjectMapper();

        log.error("Exception");

        response.getWriter().write(objectMapper.writeValueAsString(responseData));
        response.flushBuffer();
    }
}
