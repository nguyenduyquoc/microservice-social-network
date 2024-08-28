package com.hdq.profile_service.controllers;

import com.hdq.profile_service.core.BaseResponse;
import com.hdq.profile_service.exceptions.NotFoundEntityException;
import com.hdq.profile_service.services.IAccountProfileService;
import com.hdq.profile_service.utils.Constants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.REQUEST_MAPPING_PREFIX  + Constants.VERSION_API_V1 + "/profiles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountProfileController {

    IAccountProfileService accountProfileService;


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(path = "")
    BaseResponse getAllAccountProfiles() {

        return BaseResponse.success(accountProfileService.getAllProfiles());
    }


    @GetMapping(path = "/{accountId}")
    BaseResponse getAccountProfileById(@PathVariable(name = "accountId") Long accountId) {

        return BaseResponse.success(accountProfileService.getProfileByAccountId(accountId));
    }


    @GetMapping(path = "/me")
    BaseResponse getAccountProfile() {
        try {
            return BaseResponse.success(accountProfileService.getMyProfile());
        } catch (NotFoundEntityException e) {
            return BaseResponse.throwException(e);
        }
    }

}
