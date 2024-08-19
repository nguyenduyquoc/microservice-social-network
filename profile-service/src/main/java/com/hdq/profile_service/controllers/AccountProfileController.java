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
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX  + Constants.VERSION_API_V1)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountProfileController {

    IAccountProfileService accountProfileService;


    @GetMapping(path = "/{profileId}")
    BaseResponse getAccountProfile(@PathVariable(name = "profileId") String id) {

        return BaseResponse.created(accountProfileService.getProfile(id));
    }


    @PostMapping(path = "")
    BaseResponse createAccountProfile(@Valid @RequestBody AccountProfileCreateFormRequest request) {

        return BaseResponse.success(accountProfileService.createProfile(request));
    }


}
