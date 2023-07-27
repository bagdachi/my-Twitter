package com.myTwitter.userservice.repository.projection;

public interface CommonUserProjection {
    Long getId();
    String getFullName();
    String getUsername();
    String getAvatar();
    boolean isPrivateProfile();
}
