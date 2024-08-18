package com.hdq.identity_service.repositories;

import com.hdq.identity_service.core.BaseRepository;
import com.hdq.identity_service.entities.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepositoryImpl extends BaseRepository<RoleEntity> {

    RoleEntity findByName(String name);

    RoleEntity findFirstByPermissions_Id(Long id);

    boolean existsByName(String name);

    List<RoleEntity> findByNameIn(List<String> names);

}
