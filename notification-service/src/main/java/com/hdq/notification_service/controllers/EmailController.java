package com.hdq.notification_service.controllers;

import com.hdq.notification_service.core.BaseResponse;
import com.hdq.notification_service.dtos.requests.SendEmailFormRequest;
import com.hdq.notification_service.services.IEmailService;
import com.hdq.notification_service.utils.Constants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX + Constants.VERSION_API_V1 + "/email")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class EmailController {

    IEmailService emailService;

    @PostMapping(path = "")
    BaseResponse sendEmail(@RequestBody SendEmailFormRequest request) {

        return BaseResponse.success(emailService.sendEmail(request));
    }


}
