package com.hdq.notification_service.controllers;

import com.hdq.event.dto.NotificationEvent;
import com.hdq.notification_service.dtos.Receiver;
import com.hdq.notification_service.dtos.requests.SendEmailFormRequest;
import com.hdq.notification_service.services.IEmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class NotificationController {

    IEmailService emailService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void listen(NotificationEvent event) {
        log.info("Message receiver {}", event );

        emailService.sendEmail(
                SendEmailFormRequest.builder()
                        .to(Receiver.builder()
                                .email(event.getReceiver())
                                .build())
                        .subject(event.getSubject())
                        .htmlContent(event.getBody())
                        .build());
    }

}
