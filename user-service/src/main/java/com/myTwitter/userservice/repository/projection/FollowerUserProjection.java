package com.myTwitter.userservice.repository.projection;

public interface FollowerUserProjection {
    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    String getAvatar();
}
