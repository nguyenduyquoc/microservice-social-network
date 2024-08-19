package com.hdq.profile_service.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountProfileCreateFormRequest implements Serializable {

    Long accountId;
    String phone;
    String firstName;
    String lastName;
    String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;
    String city;

}
