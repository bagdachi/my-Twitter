package com.myTwitter.userservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SettingsRequest {
    private String username;
    private String email;
    private String countryCode;
    private Long phone;
    private String country;
    private String gender;
    private String language;
    private boolean mutedDirectMessages;
    private boolean privateProfile;

}
