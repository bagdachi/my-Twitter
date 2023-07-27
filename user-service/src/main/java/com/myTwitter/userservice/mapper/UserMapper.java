package com.myTwitter.userservice.mapper;


import com.myTwitter.userservice.Model.User;
import com.myTwitter.userservice.dto.request.SearchTermsRequest;
import com.myTwitter.userservice.dto.request.UserRequest;
import com.myTwitter.userservice.dto.response.AuthUserResponse;
import com.myTwitter.userservice.dto.response.SearchResultResponse;
import com.myTwitter.userservice.dto.response.UserDetailResponse;
import com.myTwitter.userservice.dto.response.UserProfileResponse;
import com.myTwitter.userservice.repository.projection.*;
import com.myTwitter.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.response.HeaderResponse;
import myTwitter.commons.dto.response.user.CommonUserResponse;
import myTwitter.commons.dto.response.user.UserResponse;
import myTwitter.commons.mapper.BasicMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BasicMapper basicMapper;
    private final UserService userService;

    public UserProfileResponse getUserById(Long userId) {
        UserProfileProjection user = userService.getUserById(userId);
        return basicMapper.convertToResponse(user, UserProfileResponse.class);
    }

    public HeaderResponse<UserResponse> getUsers(Pageable pageable) {
        Page<UserProjection> users = userService.getUsers(pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public List<UserResponse> getRelevantUsers() {
        List<UserProjection> users = userService.getRelevantUsers();
        return basicMapper.convertToResponseList(users, UserResponse.class);
    }

    public HeaderResponse<UserResponse> searchUsersByUsername(String username, Pageable pageable) {
        Page<UserProjection> users = userService.searchUsersByUsername(username, pageable, UserProjection.class);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }



    public List<CommonUserResponse> getSearchResults(SearchTermsRequest request) {
        List<CommonUserProjection> users = userService.getSearchResults(request);
        return basicMapper.convertToResponseList(users, CommonUserResponse.class);
    }

    public Boolean startUseTwitter() {
        return userService.startUseTwitter();
    }

    public AuthUserResponse updateUserProfile(UserRequest userRequest) {
        User user = basicMapper.convertToResponse(userRequest, User.class);
        AuthUserProjection authUserProjection = userService.updateUserProfile(user);
        return basicMapper.convertToResponse(authUserProjection, AuthUserResponse.class);
    }

    public Boolean processSubscribeToNotifications(Long userId) {
        return userService.processSubscribeToNotifications(userId);
    }

    public Long processPinTweet(Long tweetId) {
        return userService.processPinTweet(tweetId);
    }

    public UserDetailResponse getUserDetails(Long userId) {
        UserDetailProjection userDetails = userService.getUserDetails(userId);
        return basicMapper.convertToResponse(userDetails, UserDetailResponse.class);
    }
}
