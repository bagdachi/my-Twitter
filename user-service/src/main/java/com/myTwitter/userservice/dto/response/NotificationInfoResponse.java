package com.myTwitter.userservice.dto.response;

import lombok.Data;
import myTwitter.commons.dto.response.tweet.TweetResponse;
import myTwitter.commons.dto.response.user.UserResponse;

import java.time.LocalDateTime;

@Data
public class NotificationInfoResponse {
    private Long id;
    private LocalDateTime date;

    private UserResponse user;
    private TweetResponse tweet;
}
