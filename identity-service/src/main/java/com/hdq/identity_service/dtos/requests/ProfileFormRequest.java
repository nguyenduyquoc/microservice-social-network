package com.hdq.identity_service.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hdq.identity_service.core.BaseDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileFormRequest extends BaseDTO {

    Long accountId;
    String phone;
    String firstName;
    String lastName;
    String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;
    String city;

}
