package com.hdq.profile_service.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("account_profile")
public class AccountProfileEntity {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    String id;

    @Property(name = "account_id")
    Long accountId;

    @Property(name = "phone_number")
    String phone;

    @Property(name = "first_name")
    String firstName;

    @Property(name = "last_name")
    String lastName;

    @Property(name = "email")
    String email;

    @Property(name = "dob")
    LocalDate dob;

    @Property(name = "city")
    String city;

}
