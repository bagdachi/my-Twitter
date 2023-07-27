package com.myTwitter.userservice.mapper;


import com.myTwitter.userservice.dto.response.MutedUserResponse;
import com.myTwitter.userservice.repository.projection.MutedUserProjection;
import com.myTwitter.userservice.service.MuteUserService;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.response.HeaderResponse;
import myTwitter.commons.mapper.BasicMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MuteUserMapper {

    private final BasicMapper basicMapper;
    private final MuteUserService muteUserService;

    public HeaderResponse<MutedUserResponse> getMutedList(Pageable pageable) {
        Page<MutedUserProjection> mutedList = muteUserService.getMutedList(pageable);
        return basicMapper.getHeaderResponse(mutedList, MutedUserResponse.class);
    }

    public Boolean processMutedList(Long userId) {
        return muteUserService.processMutedList(userId);
    }
}
