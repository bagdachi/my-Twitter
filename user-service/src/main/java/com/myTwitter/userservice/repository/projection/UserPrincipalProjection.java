package com.myTwitter.userservice.repository.projection;

public interface UserPrincipalProjection {
    Long getId();
    String getEmail();
    String getActivationCode();
}
