package com.myTwitter.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import static myTwitter.commons.constants.ErrorMessage.*;


@Data
public class CurrentPasswordResetRequest {
    
    @NotBlank(message = EMPTY_CURRENT_PASSWORD)
    private String currentPassword;
    
    @NotBlank(message = EMPTY_PASSWORD)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password;
    
    @NotBlank(message = EMPTY_PASSWORD_CONFIRMATION)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password2;
}
