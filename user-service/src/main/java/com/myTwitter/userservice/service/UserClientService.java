package com.myTwitter.userservice.service;

import myTwitter.commons.dto.request.IdsRequest;
import myTwitter.commons.dto.response.HeaderResponse;
import myTwitter.commons.dto.response.lists.ListMemberResponse;
import myTwitter.commons.dto.response.user.CommonUserResponse;
import myTwitter.commons.dto.response.user.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserClientService {

    List<Long> getUserFollowersIds();



    List<Long> getSubscribersByUserId(Long userId);

    Boolean isUserFollowByOtherUser(Long userId);

    Boolean isUserHavePrivateProfile(Long userId);

    Boolean isUserBlocked(Long userId, Long blockedUserId);

    Boolean isUserBlockedByMyProfile(Long userId);

    Boolean isMyProfileBlockedByUser(Long userId);

    void increaseNotificationsCount(Long userId);

    void increaseMentionsCount(Long userId);

    void updateLikeCount(boolean increase);

    void updateTweetCount(boolean increaseCount);

    void updateMediaTweetCount(boolean increaseCount);

    CommonUserResponse getListOwnerById(Long userId);

    List<ListMemberResponse> getListParticipantsByIds(IdsRequest request);

    List<ListMemberResponse> searchListMembersByUsername(String username);







    HeaderResponse<UserResponse> getUsersByIds(IdsRequest request, Pageable pageable);



    void updatePinnedTweetId(Long tweetId);

    Long getUserPinnedTweetId(Long userId);

    List<Long> getValidTweetUserIds(IdsRequest request, String text);

    List<Long> getValidUserIds(IdsRequest request);



    Boolean isUserExists(Long userId);

    UserResponse getUserResponseById(Long userId);

    Long getUserIdByUsername(String username);



    List<Long> validateChatUsersIds(IdsRequest request);



    List<Long> getUserIdsWhichUserSubscribed();

    void resetNotificationCount();

    void resetMentionCount();
}