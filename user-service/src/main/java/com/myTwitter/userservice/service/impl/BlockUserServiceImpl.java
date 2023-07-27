package com.myTwitter.userservice.service.impl;


import com.myTwitter.userservice.repository.BlockUserRepository;
import com.myTwitter.userservice.repository.FollowerUserRepository;
import com.myTwitter.userservice.repository.projection.BlockedUserProjection;
import com.myTwitter.userservice.service.AuthenticationService;
import com.myTwitter.userservice.service.BlockUserService;
import com.myTwitter.userservice.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockUserServiceImpl implements BlockUserService {

    private final AuthenticationService authenticationService;
    private final BlockUserRepository blockUserRepository;
    private final FollowerUserRepository followerUserRepository;
    private final UserServiceHelper userServiceHelper;

    @Override
    public Page<BlockedUserProjection> getBlockList(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return blockUserRepository.getUserBlockListById(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processBlockList(Long userId) {
        return false;
    }
}
