package com.myTwitter.userservice.dto.response;


import lombok.Data;
import myTwitter.commons.dto.response.user.CommonUserResponse;

import java.util.List;

@Data
public class SearchResultResponse {
    private String text;
    private Long tweetCount;
    private List<String> tags;
    private List<CommonUserResponse> users;
}
