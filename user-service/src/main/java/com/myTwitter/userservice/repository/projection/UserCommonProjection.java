package com.myTwitter.userservice.repository.projection;

public interface UserCommonProjection {
    Long getId();
    String getEmail();
    String getFullName();
    String getActivationCode();
    String getPasswordResetCode();
}
