package com.hdq.identity_service.controllers.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginFormRequest {

    @NotNull(message = "Investor phone must not be null")
    @Pattern(regexp = "^[0-9]*$", message = "Investor phone must contain only digits")
    @Size(min = 10, max = 10, message = "Investor phone must have exactly 10 digits")
    String phone;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^.{6,}$", message = "Password must be at least 6 characters")
    String password;

}
