package com.hdq.profile_service.services;

import com.hdq.profile_service.dtos.AccountProfileDTO;
import com.hdq.profile_service.dtos.requests.AccountProfileCreateFormRequest;

public interface IAccountProfileService {

    AccountProfileDTO createProfile(AccountProfileCreateFormRequest request);

    AccountProfileDTO getProfile(String id);
}
