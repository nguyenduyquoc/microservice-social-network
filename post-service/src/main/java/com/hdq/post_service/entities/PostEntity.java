package com.hdq.post_service.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(value = "post")
public class PostEntity {

    @MongoId
    String id;
    Long accountId;
    String content;
    Instant createDate;
    Instant modifiedDate;

}
