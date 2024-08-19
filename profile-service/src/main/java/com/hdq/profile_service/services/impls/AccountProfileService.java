package com.hdq.profile_service.services.impls;

import com.hdq.profile_service.dtos.AccountProfileDTO;
import com.hdq.profile_service.dtos.requests.AccountProfileCreateFormRequest;
import com.hdq.profile_service.entities.AccountProfileEntity;
import com.hdq.profile_service.repositories.AccountProfileRepository;
import com.hdq.profile_service.services.IAccountProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class AccountProfileService implements IAccountProfileService {

    AccountProfileRepository repository;
    ModelMapper modelMapper;

    @Override
    public AccountProfileDTO createProfile(AccountProfileCreateFormRequest request) {
        AccountProfileEntity accountProfile = modelMapper.map(request, AccountProfileEntity.class);
        accountProfile = repository.save(accountProfile);
        return modelMapper.map(accountProfile, AccountProfileDTO.class);
    }

    @Override
    public AccountProfileDTO getProfile(String id) {
        AccountProfileEntity accountProfile = repository.findById(id).orElseThrow();
        return modelMapper.map(accountProfile, AccountProfileDTO.class);
    }
}
