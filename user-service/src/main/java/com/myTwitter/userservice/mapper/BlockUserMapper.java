package com.myTwitter.userservice.mapper;


import com.myTwitter.userservice.dto.response.BlockedUserResponse;
import com.myTwitter.userservice.repository.projection.BlockedUserProjection;
import com.myTwitter.userservice.service.BlockUserService;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.response.HeaderResponse;
import myTwitter.commons.mapper.BasicMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class BlockUserMapper {

    private final BasicMapper basicMapper;
    private final BlockUserService blockUserService;

    public HeaderResponse<BlockedUserResponse> getBlockList(Pageable pageable) {
        Page<BlockedUserProjection> blockList = blockUserService.getBlockList(pageable);
        return basicMapper.getHeaderResponse(blockList, BlockedUserResponse.class);
    }

    public Boolean processBlockList(Long userId) {
        return blockUserService.processBlockList(userId);
    }
}
