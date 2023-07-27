package com.myTwitter.userservice.Controller.api;

import com.myTwitter.userservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.response.user.UserPrincipalResponse;
import myTwitter.commons.mapper.BasicMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static myTwitter.commons.constants.PathConstants.API_V1_AUTH;
import static myTwitter.commons.constants.PathConstants.USER_EMAIL;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_AUTH)
public class AuthenticationApiController {

    private final AuthenticationService authenticationService;
    private final BasicMapper mapper;

    @GetMapping(USER_EMAIL)
    public UserPrincipalResponse getUserPrincipalByEmail(@PathVariable("email") String email) {
        return mapper.convertToResponse(authenticationService.getUserPrincipalByEmail(email), UserPrincipalResponse.class);
    }
}