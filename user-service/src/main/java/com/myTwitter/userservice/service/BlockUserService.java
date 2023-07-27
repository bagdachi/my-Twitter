package com.myTwitter.userservice.service;


import com.myTwitter.userservice.repository.projection.BlockedUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlockUserService {

    Page<BlockedUserProjection> getBlockList(Pageable pageable);

    Boolean processBlockList(Long userId);
}
