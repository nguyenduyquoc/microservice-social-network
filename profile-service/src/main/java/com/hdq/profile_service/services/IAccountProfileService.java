package com.hdq.profile_service.services;

import com.hdq.profile_service.dtos.AccountProfileDTO;
import com.hdq.profile_service.dtos.requests.AccountProfileCreateFormRequest;
import com.hdq.profile_service.exceptions.NotFoundEntityException;

import java.util.List;

public interface IAccountProfileService {

    AccountProfileDTO createProfile(AccountProfileCreateFormRequest request);

    AccountProfileDTO getMyProfile() throws NotFoundEntityException;

    List<AccountProfileDTO> getAllProfiles();

    AccountProfileDTO getProfileByAccountId(Long accountId);
}
