package com.myTwitter.userservice.service.impl;


import com.myTwitter.userservice.repository.FollowerUserRepository;
import com.myTwitter.userservice.repository.UserRepository;
import com.myTwitter.userservice.repository.projection.BaseUserProjection;
import com.myTwitter.userservice.repository.projection.FollowerUserProjection;
import com.myTwitter.userservice.repository.projection.UserProfileProjection;
import com.myTwitter.userservice.repository.projection.UserProjection;
import com.myTwitter.userservice.service.AuthenticationService;
import com.myTwitter.userservice.service.FollowerUserService;
import com.myTwitter.userservice.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerUserServiceImpl implements FollowerUserService {

    private final UserRepository userRepository;
    private final FollowerUserRepository followerUserRepository;
    private final AuthenticationService authenticationService;

    private final UserServiceHelper userServiceHelper;

    @Override
    public Page<UserProjection> getFollowers(Long userId, Pageable pageable) {
        userServiceHelper.validateUserProfile(userId);
        return followerUserRepository.getFollowersById(userId, pageable);
    }

    @Override
    public Page<UserProjection> getFollowing(Long userId, Pageable pageable) {
        userServiceHelper.validateUserProfile(userId);
        return followerUserRepository.getFollowingById(userId, pageable);
    }

    @Override
    public Page<FollowerUserProjection> getFollowerRequests(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return followerUserRepository.getFollowerRequests(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processFollow(Long userId) {
        userServiceHelper.checkIsUserExistOrMyProfileBlocked(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean isFollower = followerUserRepository.isFollower(authUserId, userId);
        boolean follower = false;

        if (isFollower) {
            followerUserRepository.unfollow(authUserId, userId);
            userRepository.unsubscribe(authUserId, userId);
        } else {
            boolean isPrivateProfile = userRepository.getUserPrivateProfile(userId);


                followerUserRepository.addFollowerRequest(authUserId, userId);

        }
        return follower;
    }

    @Override
    public List<BaseUserProjection> overallFollowers(Long userId) {
        userServiceHelper.validateUserProfile(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return followerUserRepository.getSameFollowers(userId, authUserId, BaseUserProjection.class);
    }

    @Override
    @Transactional
    public UserProfileProjection processFollowRequestToPrivateProfile(Long userId) {
        userServiceHelper.checkIsUserExistOrMyProfileBlocked(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean isFollowerRequest = followerUserRepository.isFollowerRequest(userId, authUserId);

        if (isFollowerRequest) {
            followerUserRepository.removeFollowerRequest(authUserId, userId);
        } else {
            followerUserRepository.addFollowerRequest(authUserId, userId);
        }
        return userRepository.getUserById(userId, UserProfileProjection.class).get();
    }

    @Override
    @Transactional
    public String acceptFollowRequest(Long userId) {
        userServiceHelper.checkIsUserExist(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        followerUserRepository.removeFollowerRequest(userId, authUserId);
        followerUserRepository.follow(userId, authUserId);
        return String.format("User (id:%s) accepted.", userId);
    }

    @Override
    @Transactional
    public String declineFollowRequest(Long userId) {
        userServiceHelper.checkIsUserExist(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        followerUserRepository.removeFollowerRequest(userId, authUserId);
        return String.format("User (id:%s) declined.", userId);
    }
}
