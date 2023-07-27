package com.myTwitter.userservice.repository.projection;


import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface AuthUserProjection { // TODO refactor
    Long getId();
    String getEmail();
    String getFullName();
    String getUsername();
    String getLocation();
    String getAbout();
    String getWebsite();
    String getCountryCode();
    Long getPhone();
    String getCountry();
    String getGender();
    String getLanguage();
    String getBirthday();
    LocalDateTime getRegistrationDate();
    Long getTweetCount();
    Long getMediaTweetCount();
    Long getLikeCount();
    Long getNotificationsCount();
    Long getMentionsCount();
    boolean isActive();
    boolean isProfileCustomized();
    boolean isProfileStarted();
    boolean isMutedDirectMessages();
    boolean isPrivateProfile();

    String getAvatar();
    String getWallpaper();
    Long getPinnedTweetId();
    Long getUnreadMessagesCount();

    @Value("#{target.followers.size()}")
    Long getFollowersSize();

    @Value("#{target.following.size()}")
    Long getFollowingSize();

    @Value("#{target.followerRequests.size()}")
    Long getFollowerRequestsSize();
}
