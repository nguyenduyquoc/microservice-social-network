package com.hdq.identity_service.repositories;

import com.hdq.identity_service.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepositoryImpl extends JpaRepository<TokenEntity, String> {
}
