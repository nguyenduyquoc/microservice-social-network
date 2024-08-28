package com.hdq.profile_service.controllers;

import com.hdq.profile_service.core.BaseResponse;
import com.hdq.profile_service.dtos.requests.AccountProfileCreateFormRequest;
import com.hdq.profile_service.services.IAccountProfileService;
import com.hdq.profile_service.utils.Constants;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX  + "/internal" + Constants.VERSION_API_V1 + "/profiles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InternalAccountProfileController {

    IAccountProfileService accountProfileService;


    @PostMapping(path = "")
    BaseResponse createAccountProfile(@Valid @RequestBody AccountProfileCreateFormRequest request) {

        return BaseResponse.created(accountProfileService.createProfile(request));
    }

}
