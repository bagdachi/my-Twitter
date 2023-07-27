package com.myTwitter.userservice.Controller.rest;

import com.myTwitter.userservice.dto.response.BlockedUserResponse;
import com.myTwitter.userservice.mapper.BlockUserMapper;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.response.HeaderResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static myTwitter.commons.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_USER)
public class BlockUserController {

    private final BlockUserMapper blockUserMapper;

    @GetMapping(BLOCKED)
    public ResponseEntity<List<BlockedUserResponse>> getBlockList(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<BlockedUserResponse> response = blockUserMapper.getBlockList(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(BLOCKED_USER_ID)
    public ResponseEntity<Boolean> processBlockList(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(blockUserMapper.processBlockList(userId));
    }
}