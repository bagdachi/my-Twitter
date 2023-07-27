package com.myTwitter.userservice.service.impl;


import com.myTwitter.userservice.Model.User;
import com.myTwitter.userservice.dto.request.SearchTermsRequest;
import com.myTwitter.userservice.feign.TweetClient;
import com.myTwitter.userservice.repository.UserRepository;
import com.myTwitter.userservice.repository.projection.*;
import com.myTwitter.userservice.service.AuthenticationService;
import com.myTwitter.userservice.service.UserService;
import com.myTwitter.userservice.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.exception.ApiRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static myTwitter.commons.constants.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;
    private final TweetClient tweetClient;


    @Override
    public UserProfileProjection getUserById(Long userId) {
        return userRepository.getUserById(userId, UserProfileProjection.class)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<UserProjection> getUsers(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.findByActiveTrueAndIdNot(authUserId, pageable);
    }

    @Override
    public List<UserProjection> getRelevantUsers() {
        return userRepository.findTop5ByActiveTrue();
    }

    @Override
    public <T> Page<T> searchUsersByUsername(String text, Pageable pageable, Class<T> type) {
        return userRepository.searchUsersByUsername(text, pageable, type);
    }




    @Override
    public List<CommonUserProjection> getSearchResults(SearchTermsRequest request) {
        return userRepository.getUsersByIds(request.getUsers(), CommonUserProjection.class);
    }

    @Override
    @Transactional
    public Boolean startUseTwitter() {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateProfileStarted(authUserId);
        return true;
    }

    @Override
    @Transactional
    public AuthUserProjection updateUserProfile(User userInfo) {
        if (userInfo.getFullName().length() == 0 || userInfo.getFullName().length() > 50) {
            throw new ApiRequestException(INCORRECT_USERNAME_LENGTH, HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();


        user.setFullName(userInfo.getFullName());
        user.setAbout(userInfo.getAbout());
        user.setLocation(userInfo.getLocation());
        user.setWebsite(userInfo.getWebsite());
        user.setProfileCustomized(true);
        return userRepository.getUserById(user.getId(), AuthUserProjection.class).get();
    }

    @Override
    @Transactional
    public Boolean processSubscribeToNotifications(Long userId) {
        userServiceHelper.checkIsUserExistOrMyProfileBlocked(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean isUserSubscribed = userRepository.isUserSubscribed(userId, authUserId);

        if (isUserSubscribed) {
            userRepository.unsubscribe(authUserId, userId);
            return false;
        } else {
            userRepository.subscribe(authUserId, userId);
            return true;
        }
    }

    @Override
    @Transactional
    public Long processPinTweet(Long tweetId) {
        if (!tweetClient.isTweetExists(tweetId)) {
            throw new ApiRequestException(TWEET_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        Long pinnedTweetId = userRepository.getPinnedTweetId(authUserId);

        if (pinnedTweetId == null || !pinnedTweetId.equals(tweetId)) {
            userRepository.updatePinnedTweetId(tweetId, authUserId);
            return tweetId;
        } else {
            userRepository.updatePinnedTweetId(null, authUserId);
            return 0L;
        }
    }

    @Override
    public UserDetailProjection getUserDetails(Long userId) {
        userServiceHelper.checkIsUserExistOrMyProfileBlocked(userId);
        return userRepository.getUserById(userId, UserDetailProjection.class)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
