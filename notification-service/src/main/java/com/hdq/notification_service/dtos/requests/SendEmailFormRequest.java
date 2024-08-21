package com.hdq.notification_service.dtos.requests;

import com.hdq.notification_service.dtos.Receiver;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendEmailFormRequest {

    Receiver to;
    String subject;
    String htmlContent;
}
