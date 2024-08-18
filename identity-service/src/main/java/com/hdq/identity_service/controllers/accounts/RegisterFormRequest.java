package com.hdq.identity_service.controllers.accounts;

import com.hdq.identity_service.core.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterFormRequest extends BaseDTO {

    @NotBlank(message = "Phone is mandatory")
    @Size(max = 225, message = "First name must be lest than 255 characters")
    String phone;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;

    String firstName;

    String lastName;

    String email;

    LocalDate dob;

    String city;

}
