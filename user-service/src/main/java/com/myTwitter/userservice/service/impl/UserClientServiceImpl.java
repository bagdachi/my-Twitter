package com.myTwitter.userservice.service.impl;


import com.myTwitter.userservice.repository.BlockUserRepository;
import com.myTwitter.userservice.repository.FollowerUserRepository;
import com.myTwitter.userservice.repository.UserRepository;
import com.myTwitter.userservice.repository.projection.*;
import com.myTwitter.userservice.service.AuthenticationService;
import com.myTwitter.userservice.service.UserClientService;
import com.myTwitter.userservice.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.request.IdsRequest;
import myTwitter.commons.dto.response.HeaderResponse;
import myTwitter.commons.dto.response.lists.ListMemberResponse;
import myTwitter.commons.dto.response.user.CommonUserResponse;
import myTwitter.commons.dto.response.user.UserResponse;
import myTwitter.commons.mapper.BasicMapper;
import myTwitter.commons.util.AuthUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserClientServiceImpl implements UserClientService {

    private final UserRepository userRepository;
    private final FollowerUserRepository followerUserRepository;
    private final BlockUserRepository blockUserRepository;
    private final BasicMapper basicMapper;
    private final AuthenticationService authenticationService;
    private final UserServiceHelper userServiceHelper;

    @Override
    public List<Long> getUserFollowersIds() {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        List<Long> userFollowersIds = followerUserRepository.getUserFollowersIds(authUserId);
        userFollowersIds.add(authUserId);
        return userFollowersIds;
    }



    @Override
    public List<Long> getSubscribersByUserId(Long userId) {
        return userRepository.getSubscribersByUserId(userId);
    }

    @Override
    public Boolean isUserFollowByOtherUser(Long userId) {
        return userServiceHelper.isUserFollowByOtherUser(userId);
    }

    @Override
    public Boolean isUserHavePrivateProfile(Long userId) {
        return userServiceHelper.isUserHavePrivateProfile(userId);
    }

    @Override
    public Boolean isUserBlocked(Long userId, Long blockedUserId) {
        return blockUserRepository.isUserBlocked(userId, blockedUserId);
    }

    @Override
    public Boolean isUserBlockedByMyProfile(Long userId) {
        return userServiceHelper.isUserBlockedByMyProfile(userId);
    }

    @Override
    public Boolean isMyProfileBlockedByUser(Long userId) {
        return userServiceHelper.isMyProfileBlockedByUser(userId);
    }

    @Override
    @Transactional
    public void increaseNotificationsCount(Long userId) {
        userRepository.increaseNotificationsCount(userId);
    }

    @Override
    @Transactional
    public void increaseMentionsCount(Long userId) {
        userRepository.increaseMentionsCount(userId);
    }

    @Override
    @Transactional
    public void updateLikeCount(boolean increaseCount) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        userRepository.updateLikeCount(increaseCount, userId);
    }

    @Override
    @Transactional
    public void updateTweetCount(boolean increaseCount) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        userRepository.updateTweetCount(increaseCount, userId);
    }

    @Override
    @Transactional
    public void updateMediaTweetCount(boolean increaseCount) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        userRepository.updateMediaTweetCount(increaseCount, userId);
    }

    @Override
    public CommonUserResponse getListOwnerById(Long userId) {
        CommonUserProjection user = userRepository.getUserById(userId, CommonUserProjection.class).get();
        return basicMapper.convertToResponse(user, CommonUserResponse.class);
    }



    @Override
    public List<ListMemberResponse> getListParticipantsByIds(IdsRequest request) {
        List<ListMemberProjection> users = userRepository.getUsersByIds(request.getIds(), ListMemberProjection.class);
        return basicMapper.convertToResponseList(users, ListMemberResponse.class);
    }

    @Override
    public List<ListMemberResponse> searchListMembersByUsername(String username) {
        List<ListMemberProjection> users = userRepository.searchListMembersByUsername(username);
        return basicMapper.convertToResponseList(users, ListMemberResponse.class);
    }





    @Override
    public HeaderResponse<UserResponse> getUsersByIds(IdsRequest request, Pageable pageable) {
        Page<UserProjection> users = userRepository.getUsersByIds(request.getIds(), pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }



    @Override
    @Transactional
    public void updatePinnedTweetId(Long tweetId) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        Long pinnedTweetId = userRepository.getPinnedTweetId(userId);

        if (pinnedTweetId != null && pinnedTweetId.equals(tweetId)) {
            userRepository.updatePinnedTweetId(null, userId);
        }
    }

    @Override
    public Long getUserPinnedTweetId(Long userId) {
        return userRepository.getPinnedTweetId(userId);
    }

    @Override
    public List<Long> getValidTweetUserIds(IdsRequest request, String text) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<Long> validUserIds = userRepository.getValidUserIdsByIds(request.getIds(), authUserId);
        List<Long> userIdsByUsername = userRepository.getValidUserIdsByName(text, request.getIds());
        return Stream.concat(validUserIds.stream(), userIdsByUsername.stream()).distinct().toList();
    }

    @Override
    public List<Long> getValidUserIds(IdsRequest request) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<Long> blockedUserIds = userRepository.getUserIdsWhoBlockedMyProfile(request.getIds(), authUserId);
        request.getIds().removeAll(blockedUserIds);
        return userRepository.getValidUserIdsByIds(request.getIds(), authUserId);
    }



    @Override
    public Boolean isUserExists(Long userId) {
        return userRepository.isUserExists(userId);
    }

    @Override
    public UserResponse getUserResponseById(Long userId) {
        UserProjection user = userRepository.getUserById(userId, UserProjection.class).get();
        return basicMapper.convertToResponse(user, UserResponse.class);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username.substring(1));
    }



    @Override
    public List<Long> validateChatUsersIds(IdsRequest request) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<Long> blockedUserIds = userRepository.getUserIdsWhoBlockedMyProfile(request.getIds(), authUserId);
        request.getIds().removeAll(blockedUserIds);
        return request.getIds();
    }



    @Override
    public List<Long> getUserIdsWhichUserSubscribed() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.getUserIdsWhichUserSubscribed(authUserId);
    }

    @Override
    @Transactional
    public void resetNotificationCount() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        userRepository.resetNotificationCount(authUserId);
    }

    @Override
    @Transactional
    public void resetMentionCount() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        userRepository.resetMentionCount(authUserId);
    }
}