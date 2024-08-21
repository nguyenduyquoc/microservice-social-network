package com.hdq.notification_service.dtos.requests;

import com.hdq.notification_service.dtos.Receiver;
import com.hdq.notification_service.dtos.Sender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailFormRequest {

    Sender sender;
    List<Receiver> to;
    String subject;
    String htmlContent;
}
