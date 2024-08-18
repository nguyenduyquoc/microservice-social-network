package com.hdq.identity_service.repositories;

import com.hdq.identity_service.core.BaseRepository;
import com.hdq.identity_service.entities.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepositoryImpl extends BaseRepository<AccountEntity> {

    boolean existsByPhone(String phone);
    Optional<AccountEntity> findByPhone(String phone);
    AccountEntity findFirstByRoles_Id(Long id);

}
