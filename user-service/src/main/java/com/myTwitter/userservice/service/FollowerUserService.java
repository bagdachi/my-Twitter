package com.myTwitter.userservice.service;


import com.myTwitter.userservice.repository.projection.BaseUserProjection;
import com.myTwitter.userservice.repository.projection.FollowerUserProjection;
import com.myTwitter.userservice.repository.projection.UserProfileProjection;
import com.myTwitter.userservice.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowerUserService {

    Page<UserProjection> getFollowers(Long userId, Pageable pageable);

    Page<UserProjection> getFollowing(Long userId, Pageable pageable);

    Page<FollowerUserProjection> getFollowerRequests(Pageable pageable);

    Boolean processFollow(Long userId);

    List<BaseUserProjection> overallFollowers(Long userId);

    UserProfileProjection processFollowRequestToPrivateProfile(Long userId);

    String acceptFollowRequest(Long userId);

    String declineFollowRequest(Long userId);
}
