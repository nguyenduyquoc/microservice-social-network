package com.hdq.profile_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountProfileDTO implements Serializable {

    String id;
    Long accountId;
    String phone;
    String firstName;
    String lastName;
    String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    String city;

}
