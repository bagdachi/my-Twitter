package com.myTwitter.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import static myTwitter.commons.constants.ErrorMessage.*;


@Data
public class AuthenticationRequest {

    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = EMPTY_PASSWORD)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password;
}
