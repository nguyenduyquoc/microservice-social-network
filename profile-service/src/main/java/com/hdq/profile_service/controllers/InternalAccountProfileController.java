package com.hdq.profile_service.controllers;

import com.hdq.profile_service.core.BaseResponse;
import com.hdq.profile_service.dtos.AccountProfileDTO;
import com.hdq.profile_service.dtos.requests.AccountProfileCreateFormRequest;
import com.hdq.profile_service.exceptions.NotFoundEntityException;
import com.hdq.profile_service.services.IAccountProfileService;
import com.hdq.profile_service.utils.Constants;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX  + Constants.VERSION_API_V1)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InternalAccountProfileController {

    IAccountProfileService accountProfileService;


    @PostMapping(path = "/internal/accounts")
    BaseResponse createAccountProfile(@Valid @RequestBody AccountProfileCreateFormRequest request) {

        return BaseResponse.created(accountProfileService.createProfile(request));
    }

    @GetMapping(path = "/internal/accounts/{accountId}")
    Object getAccountProfile(@PathVariable(name = "accountId") Long accountId) {

        return accountProfileService.getProfileByUserId(accountId);

    }


}
