package com.myTwitter.userservice.service;

import com.myTwitter.userservice.Model.User;
import com.myTwitter.userservice.dto.request.SearchTermsRequest;
import com.myTwitter.userservice.repository.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserProfileProjection getUserById(Long userId);

    Page<UserProjection> getUsers(Pageable pageable);

    List<UserProjection> getRelevantUsers();

    <T> Page<T> searchUsersByUsername(String username, Pageable pageable, Class<T> type);



    List<CommonUserProjection> getSearchResults(SearchTermsRequest request);

    Boolean startUseTwitter();

    AuthUserProjection updateUserProfile(User userInfo);

    Boolean processSubscribeToNotifications(Long userId);

    Long processPinTweet(Long tweetId);

    UserDetailProjection getUserDetails(Long userId);
}
