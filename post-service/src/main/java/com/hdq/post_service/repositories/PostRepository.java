package com.hdq.post_service.repositories;

import com.hdq.post_service.entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<PostEntity, String> {

    Page<PostEntity> findByAccountId(Long accountId, Pageable pageable);
}