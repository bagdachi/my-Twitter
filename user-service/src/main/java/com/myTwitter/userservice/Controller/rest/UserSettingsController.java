package com.myTwitter.userservice.Controller.rest;

import com.myTwitter.userservice.dto.request.SettingsRequest;
import com.myTwitter.userservice.dto.response.AuthenticationResponse;
import com.myTwitter.userservice.dto.response.UserPhoneResponse;
import com.myTwitter.userservice.mapper.UserSettingsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static myTwitter.commons.constants.PathConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_USER_SETTINGS_UPDATE)
public class UserSettingsController {

    private final UserSettingsMapper userSettingsMapper;

    @PutMapping(USERNAME)
    public ResponseEntity<String> updateUsername(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateUsername(request));
    }

    @PutMapping(EMAIL)
    public ResponseEntity<AuthenticationResponse> updateEmail(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateEmail(request));
    }

    @PutMapping(PHONE)
    public ResponseEntity<UserPhoneResponse> updatePhone(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updatePhone(request));
    }

    @PutMapping(COUNTRY)
    public ResponseEntity<String> updateCountry(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateCountry(request));
    }

    @PutMapping(GENDER)
    public ResponseEntity<String> updateGender(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateGender(request));
    }

    @PutMapping(LANGUAGE)
    public ResponseEntity<String> updateLanguage(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateLanguage(request));
    }

    @PutMapping(DIRECT)
    public ResponseEntity<Boolean> updateDirectMessageRequests(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateDirectMessageRequests(request));
    }

    @PutMapping(PRIVATE)
    public ResponseEntity<Boolean> updatePrivateProfile(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updatePrivateProfile(request));
    }


}