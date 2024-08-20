package com.hdq.profile_service.repositories;

import com.hdq.profile_service.entities.AccountProfileEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProfileRepository extends Neo4jRepository<AccountProfileEntity, String> {

    AccountProfileEntity findByAccountId(Long accountId);
}
