package com.myTwitter.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;


import static myTwitter.commons.constants.ErrorMessage.*;

@Data
public class RegistrationRequest {

    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = BLANK_NAME)
    @Size(min = 1, max = 50, message = NAME_NOT_VALID)
    private String username;

    private String birthday;
}
