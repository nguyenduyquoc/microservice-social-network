package com.hdq.profile_service.services.impls;

import com.hdq.profile_service.dtos.AccountProfileDTO;
import com.hdq.profile_service.dtos.requests.AccountProfileCreateFormRequest;
import com.hdq.profile_service.entities.AccountProfileEntity;
import com.hdq.profile_service.exceptions.NotFoundEntityException;
import com.hdq.profile_service.repositories.AccountProfileRepository;
import com.hdq.profile_service.services.IAccountProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class AccountProfileServiceImpl implements IAccountProfileService {

    AccountProfileRepository repository;
    ModelMapper modelMapper;

    @Override
    public AccountProfileDTO createProfile(AccountProfileCreateFormRequest request) {
        AccountProfileEntity accountProfile = modelMapper.map(request, AccountProfileEntity.class);
        accountProfile = repository.save(accountProfile);
        return modelMapper.map(accountProfile, AccountProfileDTO.class);
    }

    @Override
    public AccountProfileDTO getMyProfile() throws NotFoundEntityException {
        Long accountId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        AccountProfileEntity accountProfile = repository.findByAccountId(accountId);
        if (accountProfile == null)
            throw new NotFoundEntityException("Profile", accountId);

        return modelMapper.map(accountProfile, AccountProfileDTO.class);
    }

    @Override
    public List<AccountProfileDTO> getAllProfiles() {
        var profiles = repository.findAll();

        return profiles.stream().map(accountProfile ->
            modelMapper.map(accountProfile, AccountProfileDTO.class)
        ).toList();
    }

    @Override
    public AccountProfileDTO getProfileByAccountId(Long accountId) {

        AccountProfileEntity accountProfile = repository.findByAccountId(accountId);
        return modelMapper.map(accountProfile, AccountProfileDTO.class);

    }
}
