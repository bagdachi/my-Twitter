package com.myTwitter.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import static myTwitter.commons.constants.ErrorMessage.EMAIL_NOT_VALID;

@Data
public class ProcessEmailRequest {
    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;
}
