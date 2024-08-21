package com.hdq.notification_service.services.impls;

import com.hdq.notification_service.dtos.Sender;
import com.hdq.notification_service.dtos.requests.EmailFormRequest;
import com.hdq.notification_service.dtos.requests.SendEmailFormRequest;
import com.hdq.notification_service.dtos.responses.EmailResponse;
import com.hdq.notification_service.exception.CustomException;
import com.hdq.notification_service.repositories.http_clients.EmailClient;
import com.hdq.notification_service.services.IEmailService;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailServiceImpl implements IEmailService {

    EmailClient emailClient;
    MessageSource messageSource;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;
    @Override
    public EmailResponse sendEmail(SendEmailFormRequest request) {
        EmailFormRequest emailFormRequest = EmailFormRequest.builder()
                .sender(Sender.builder()
                        .name("Nguyen Duy Quoc")
                        .email("nguyenduyquoc129829042001@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailFormRequest);
        } catch (FeignException e) {
            throw new CustomException(messageSource, "cannot-send-email");
        }

    }
}
