package com.hdq.notification_service.services;

import com.hdq.notification_service.dtos.requests.SendEmailFormRequest;
import com.hdq.notification_service.dtos.responses.EmailResponse;

public interface IEmailService {

    EmailResponse sendEmail(SendEmailFormRequest request);
}
