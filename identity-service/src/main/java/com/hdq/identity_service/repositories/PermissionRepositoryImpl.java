package com.hdq.identity_service.repositories;

import com.hdq.identity_service.core.BaseRepository;
import com.hdq.identity_service.entities.PermissionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepositoryImpl extends BaseRepository<PermissionEntity> {

    boolean existsByNameIgnoreCase(String name);
}
